package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;

/**
 * Critters exposed on the board
 */
public class BoardCritters extends Cards {
  public void clearSummoning() {
    visit(c -> c.resType.clearSummoning(c));
  }
}
