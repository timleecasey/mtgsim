package org.tlc.mtg.nouns;

/**
 */
public class CostSpec {
    public String mana;
    public boolean hasCost;

    void bind(RawCard raw) {

        String rawCost = ((String)raw.getFieldByName("manaCost"));
        if( rawCost != null )
            rawCost = rawCost.replaceAll("[\\{\\}]", "");
        this.mana = rawCost;
        if( this.mana == null )
            hasCost = false;
        else
            hasCost = true;
    }

    @Override
    public String toString() {
        if( hasCost )
            return mana;
        else
            return "";
    }
}
