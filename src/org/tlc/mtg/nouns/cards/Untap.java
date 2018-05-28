package org.tlc.mtg.nouns.cards;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Stage;
import org.tlc.mtg.nouns.StageNode;

/**
 */
public class Untap extends Stage<Card> {

  public Untap() {
    nodes.add(StageNode.UNTAP);
    func = c -> { c.tapped = false; return c; };
  }

}
