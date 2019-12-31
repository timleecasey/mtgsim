package org.tlc.mtg.nouns;

import org.tlc.mtg.nouns.cards.Tap;
import org.tlc.mtg.nouns.cards.Untap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the state of a card within a specific game.  Cards cannot be used between games.
 */
public class Card {
  public String name;
  public String type;
  public ResolvedType resType;
  public CostSpec cost = new CostSpec();
  public CreatureSpec animal = new CreatureSpec();
  public List<Card> attached = new ArrayList<>();
  public boolean tapped = false;
  public Mana[] manaSrc;
  public String text;
  /**
   * Keywords
   */
  public Map<Phases, Phase<Card>> phases = new HashMap<>();

  public void applyPhase(Phases p) {
    Phase<Card> s = phases.get(p);
    if( s == null ) {
      return;
    }
    s.func.apply(this);
  }

  public void bind(RawCard raw, CardBinder binder) {
    buildStages();
    binder.bind(this, raw);
  }

  public static Card makeCopy(Card that) {
    Card ret = new Card();
    ret.phases.putAll(that.phases);
    ret.name = that.name;
    ret.type = that.type;
    ret.cost = that.cost;
    ret.animal = that.animal;
    ret.resType = that.resType;
    return ret;
  }

  protected void buildStages() {
    phases.put(Phases.ATTACK_ADD, new Tap());
    phases.put(Phases.TAP, new Tap());
    phases.put(Phases.UNTAP, new Untap());
  }

  public String toString() {
    if( cost == null || ! cost.hasCost )
      return name + (tapped ? " - " : " | ");
    return name +
        ": " +
        cost +
        (animal.hasPwr ? (" " + animal) : "") +
        (tapped ? " - " : " | ");
  }

  public long computeDamage(int step, int len) {
    if( animal.hasPwr ) {
      return (len - step) * (long)animal.power;
    }
    return 0;
  }
}
