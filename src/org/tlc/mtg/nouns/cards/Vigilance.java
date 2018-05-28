package org.tlc.mtg.nouns.cards;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Stage;
import org.tlc.mtg.nouns.StageNode;

/**
 */
public class Vigilance extends Stage<Card> {
  public Vigilance() {
    nodes.add(StageNode.ATTACK_ADD);
    nodes.add(StageNode.TAP);
    func = c -> { c.tapped = true; return c; };
  }
}
