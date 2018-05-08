package org.tlc.mtg.nouns;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Card {
    public String name;
    public String type;
    public ResolvedType resType;
    public CostSpec cost;
    public CreatureSpec animal;
    public List<Card> attached = new ArrayList<>();

    public void bind(RawCard raw) {
        this.name = raw.getName();
        this.type = raw.getType();
        this.resType = ResolvedType.resolve(this.type);
        this.cost = new CostSpec();
        this.cost.bind(raw);
        this.animal = new CreatureSpec();
        this.animal.bind(raw);
    }

    public static Card makeCopy(Card that) {
        Card ret = new Card();
        ret.name = that.name;
        ret.type = that.type;
        ret.cost = that.cost;
        ret.animal = that.animal;
        return ret;
    }

    public String toString() {
        if( cost == null || ! cost.hasCost )
            return name;
        return name + ": " + cost + (animal.hasPwr ? (" " + animal) : "");
    }

    public long computeDamage(int step, int len) {
        if( animal.hasPwr ) {
            return (len - step) * (long)animal.power;
        }
        return 0;
    }
}
