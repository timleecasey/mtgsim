package org.tlc.mtg.nouns;

/**
 */
public class Card {
    public String name;
    public String type;
    public CostSpec cost;
    public CreatureSpec animal;

    public void bind(RawCard raw) {
        this.name = raw.getName();
        this.type = raw.getType();
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
        return name + ": " + cost;
    }
}
