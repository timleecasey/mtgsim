package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;

/**
 */
public class CardStack extends Cards {
  @Override
  public void add(Card c) {
    cards.add(c);
  }

  @Override
  public Card remove() {
    if( cards.size() == 0 ) {
      return null;
    }

    int index = cards.size() - 1;
    return cards.remove(index);
  }
}
