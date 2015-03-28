package org.tlc.mtg.nouns;

/**
 */
public class CostSpec {
    public String mana;

    void bind(RawCard raw) {

        String rawCost = ((String)raw.getFieldByName("manaCost"));
        if( rawCost != null )
            rawCost = rawCost.replaceAll("[\\{\\}]", "");
        this.mana = rawCost;
    }

    @Override
    public String toString() {
        return mana;
    }
}
