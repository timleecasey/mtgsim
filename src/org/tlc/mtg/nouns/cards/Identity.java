package org.tlc.mtg.nouns.cards;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Phases;
import org.tlc.mtg.nouns.Stage;

/**
 */
public class Identity extends Stage<Card> {
  public Identity(Phases... phases) {
    for( Phases p : phases) {
      this.phases.add(p);
    }
    func = c -> c;
  }
}
