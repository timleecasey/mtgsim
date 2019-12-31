package org.tlc.mtg.nouns.grammar;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Phase;
import org.tlc.mtg.nouns.Phases;

import java.util.function.Function;

/**
*/
class CardMod {
  Phases phase;
  Function<Card, Card> func;

  public CardMod(Phases phase, Function<Card, Card> func) {
    this.phase = phase;
    this.func = func;
  }

}
