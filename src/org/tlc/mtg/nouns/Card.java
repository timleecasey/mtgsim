package org.tlc.mtg.nouns;

import org.tlc.mtg.nouns.cards.Identity;
import org.tlc.mtg.nouns.cards.Tap;
import org.tlc.mtg.nouns.cards.Untap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class Card {
  public String name;
  public String type;
  public ResolvedType resType;
  public CostSpec cost;
  public CreatureSpec animal;
  public List<Card> attached = new ArrayList<>();
  public boolean tapped = false;
  /**
   * Keywords
   */
  public Map<Phases, Stage<Card>> stages = new HashMap<>();

  public void bind(RawCard raw) {
    buildStages();
    this.name = raw.getName();
    this.type = raw.getType();
    this.resType = ResolvedType.resolve(this.type);
    this.cost = new CostSpec();
    this.cost.bind(raw);
    this.animal = new CreatureSpec();
    this.animal.bind(raw);
    if( this.animal.vigilance ) {
      stages.put(Phases.ATTACK, new Identity(Phases.ATTACK));
    }
    if( this.animal.defender ) {
      stages.remove(Phases.ATTACK);
    }
  }

  public static Card makeCopy(Card that) {
    Card ret = new Card();
    ret.name = that.name;
    ret.type = that.type;
    ret.cost = that.cost;
    ret.animal = that.animal;
    ret.resType = that.resType;
    return ret;
  }

  protected void buildStages() {
    stages.put(Phases.ATTACK, new Tap());
    stages.put(Phases.TAP, new Tap());
    stages.put(Phases.UNTAP, new Untap());
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
