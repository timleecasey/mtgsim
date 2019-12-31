package org.tlc.mtg.nouns.cards;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Phase;
import org.tlc.mtg.nouns.Phases;

/**
 */
public class Untap extends Phase<Card> {

  public Untap() {
    phases.add(Phases.UNTAP);
    func = c -> {
      if( c == null )
        return null;
      c.tapped = false;
      if( c.animal != null ) {
        c.animal.sick = false;
      }
      return c;
    };
  }

}
