package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;
import org.tlc.mtg.nouns.Phase;
import org.tlc.mtg.nouns.Phases;

import java.util.List;

/**
 * Holder of player phases
 */
public class PlayerPhases {

  public PlayerPhases() {
  }

  public static class Untap extends Phase<Player> {
    public Untap() {
      phases.add(Phases.UNTAP);
      func = p -> {
        p.getBoard().visitBoard(new Board.PhaseVisitor(Phases.UNTAP));
        return p;
      };
    }
  }

  public static class Upkeep extends Phase<Player> {
    public Upkeep() {
      phases.add(Phases.UPKEEP);
      func = p -> {
        p.getBoard().visitBoard(new Board.PhaseVisitor(Phases.UPKEEP));
        return p;
      };
    }
  }

  public static class Draw extends Phase<Player> {
    public Draw() {
      phases.add(Phases.DRAW);
      func = p -> {
        Card c = p.getDeck().pullTopOne();
        c.applyPhase(Phases.DRAW);
        p.getHand().add(c);
        return p;
      };
    }
  }

  public static class Spells extends Phase<Player> {
    public Spells() {
      phases.add(Phases.CAST);
      func = p -> {
        //
        // greedy
        //
        Card c;
        while( ( c = p.findTopCast()) != null ) {
          p.castSpell(c);
          p.getCur().critter++;
        }
        return p;
      };
    }
  }

  public static class Land extends Phase<Player> {
    public Land() {
      phases.add(Phases.LAND);
      func = p -> {
        Card c;
        if( (c = p.getHand().hasOneOrNull(ResolvedType.LAND)) != null ) {
          if( ! p.getHand().takeSpecific(c) ) {
            throw new IllegalStateException("Could not pull something found");
          }
          p.placeLand(c);
          p.getCur().land++;
        }

        if( p.getTurn() > 9 && p.getBoard().getLand().depth() == 0 ) {
          p.setConstrained(true);
        }

        return p;
      };
    }
  }

  public static class Attack extends Phase<Player> {
    public Attack() {
      phases.add(Phases.CLEANUP);
      func = p -> p;
    }
  }


  public static class CleanUp extends Phase<Player> {
    public CleanUp() {
      phases.add(Phases.CLEANUP);
      func = p -> p;
    }
  }

  public static class End extends Phase<Player> {
    public End() {
      phases.add(Phases.END);
      func = p -> {
        while( p.getHand().depth() > 7 ) {
          Card c = p.getHand().pullTopOne();
          p.getBoard().getGrave().add(c);
          p.getCur().discard++;
        }
        return p;
      };
    }
  }

  public void assignDeck(Player player, List<Card> cards) {
    player.resetPlayer(cards.size());
    player.getDeck().resetAndAddAllCards(cards);
  }

  public void assignPhases(Player player) {
    player.getPhases().clear();
    player.getPhases().add(new Untap());
    player.getPhases().add(new Upkeep());
    player.getPhases().add(new Draw());
    player.getPhases().add(new Land());
    player.getPhases().add(new Spells());
    player.getPhases().add(new Attack());
    player.getPhases().add(new CleanUp());
    player.getPhases().add(new End());
  }

  public void playout(Player player) {
    player.initialDraw();
    while( player.getDeck().hasSome() ) {
      turn(player);
      if( player.isConstrained() ) {
        break;
      }
    }
  }

  public void turn(Player player) {
    player.incTurnAndCur();

    for( Phase<Player> s : player.getPhases() ) {
      s.func.apply(player);
    }
  }

}
