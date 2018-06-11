package org.tlc.mtg.nouns.cards;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Phase;
import org.tlc.mtg.nouns.Phases;

/**
 */
public class Tap extends Phase<Card> {
  public Tap() {
    phases.add(Phases.ATTACK_ADD);
    phases.add(Phases.TAP);
    func = c -> { c.tapped = true; return c; };
  }
}
