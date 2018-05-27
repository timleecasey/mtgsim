package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;

import java.util.List;

/**
 * Holder of player phases
 */
public class PlayerTurnPhases {
  private Player player;

  public PlayerTurnPhases() {
    player = new Player();
  }

  private void untap() {
    player.getBoard().getCritters().clearSummoning();
    player.getBoard().untap();
  }

  public void upkeep() {

  }

  public void draw() {
    Card c = player.getDeck().pullTopOne();
    player.getHand().add(c);
  }

  public void spells() {
    //
    // greedy
    //
    Card c;
    while( ( c = player.findTopCast()) != null ) {
      player.castSpell(c);
    }
  }

  public void land() {
    Card c;
    if( (c = player.getHand().hasOneOrNull(ResolvedType.LAND)) != null ) {
      if( ! player.getHand().takeSpecific(c) ) {
        throw new IllegalStateException("Could not pull something found");
      }
      player.placeLand(c);
    }

    if( player.getTurn() > 9 && player.getBoard().getLand().depth() == 0 ) {
      player.setConstrained(true);
    }
  }

  public void attack() {

  }

  public void cleanup() {

  }

  public void end() {
    while( player.getHand().depth() > 7 ) {
      Card c = player.getHand().pullTopOne();
      player.getBoard().getGrave().add(c);
    }
  }

  public void assignDeck(List<Card> cards) {
    player.resetPlayer();
    player.getDeck().resetAndAddAllCards(cards);
  }

  public void playout() {
    player.initialDraw();
    while( player.getDeck().hasSome() ) {
      player.incTurn();
      untap();
      upkeep();
      draw();
      land();
      spells();
      attack();
      cleanup();
      end();

      if( player.isConstrained() ) {
        break;
      }
    }
  }

  public Player getPlayer() {
    return player;
  }
}
