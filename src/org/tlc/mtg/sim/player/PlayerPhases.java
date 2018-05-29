package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;
import org.tlc.mtg.nouns.Stage;
import org.tlc.mtg.nouns.Phases;

import java.util.List;

/**
 * Holder of player phases
 */
public class PlayerPhases {
  private Player player;

  public PlayerPhases() {
    player = new Player();
  }

  public static class Untap extends Stage<Player> {
    public Untap() {
      phases.add(Phases.UNTAP);
      func = p -> {
        p.getBoard().getCritters().clearSummoning();
        p.getBoard().untap();
        return p;
      };
    }
  }

  public static class Upkeep extends Stage<Player> {
    public Upkeep() {
      phases.add(Phases.UPKEEP);
      func = p -> p;
    }
  }

  public static class Draw extends Stage<Player> {
    public Draw() {
      phases.add(Phases.DRAW);
      func = p -> {
        Card c = p.getDeck().pullTopOne();
        p.getHand().add(c);
        return p;
      };
    }
  }

  public static class Spells extends Stage<Player> {
    public Spells() {
      phases.add(Phases.CAST);
      func = p -> {
        //
        // greedy
        //
        Card c;
        while( ( c = p.findTopCast()) != null ) {
          p.castSpell(c);
        }
        return p;
      };
    }
  }

  public static class Land extends Stage<Player> {
    public Land() {
      phases.add(Phases.LAND);
      func = p -> {
        Card c;
        if( (c = p.getHand().hasOneOrNull(ResolvedType.LAND)) != null ) {
          if( ! p.getHand().takeSpecific(c) ) {
            throw new IllegalStateException("Could not pull something found");
          }
          p.placeLand(c);
        }

        if( p.getTurn() > 9 && p.getBoard().getLand().depth() == 0 ) {
          p.setConstrained(true);
        }

        return p;
      };
    }
  }

  public static class Attack extends Stage<Player> {
    public Attack() {
      phases.add(Phases.CLEANUP);
      func = p -> p;
    }
  }


  public static class CleanUp extends Stage<Player> {
    public CleanUp() {
      phases.add(Phases.CLEANUP);
      func = p -> p;
    }
  }

  public static class End extends Stage<Player> {
    public End() {
      phases.add(Phases.END);
      func = p -> {
        while( p.getHand().depth() > 7 ) {
          Card c = p.getHand().pullTopOne();
          p.getBoard().getGrave().add(c);
        }
        return p;
      };
    }
  }

  public void assignDeck(List<Card> cards) {
    player.resetPlayer();
    player.getStages().clear();
    player.getStages().add(new Untap());
    player.getStages().add(new Upkeep());
    player.getStages().add(new Draw());
    player.getStages().add(new Land());
    player.getStages().add(new Spells());
    player.getStages().add(new Attack());
    player.getStages().add(new CleanUp());
    player.getStages().add(new End());
    player.getDeck().resetAndAddAllCards(cards);
  }

  public void playout() {
    player.initialDraw();
    while( player.getDeck().hasSome() ) {
      player.incTurn();

      for( Stage<Player> s : player.getStages() ) {
        s.func.apply(player);
      }

      if( player.isConstrained() ) {
        break;
      }
    }
  }

  public Player getPlayer() {
    return player;
  }
}