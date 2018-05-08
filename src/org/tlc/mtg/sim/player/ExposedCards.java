package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;

/**
 */
public class ExposedCards extends Cards {
  @Override
  public void add(Card c) {
    cards.add(c);
  }

  @Override
  public Card remove() {
    return null;
  }
}
