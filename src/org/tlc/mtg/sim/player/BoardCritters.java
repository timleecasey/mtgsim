package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;

/**
 * Critters exposed on the board
 */
public class BoardCritters extends Cards {
  public void clearSummoning() {
    visit(new CardVisitor() {
      @Override
      public void visit(Card c) {
        if( c.resType.equals(ResolvedType.CRITTER) ) {
          c.animal.sick = false;
        }
      }
    });
  }
}
