package org.tlc.mtg.nouns;

import java.util.Map;

/**
 */
public class CreatureSpec {
    public int power;
    public int toughness;
    public boolean hasPwr;

    void bind(RawCard raw) {
        String pStr = (String) raw.getFieldByName("power");
        String tStr = (String) raw.getFieldByName("toughness");

        if( pStr == null || tStr == null ) {
            hasPwr = false;
            return;
        }

        hasPwr = true;
        power = Integer.valueOf(pStr);
        toughness = Integer.valueOf(tStr);
    }

    @Override
    public String toString() {
        if( ! hasPwr )
            return "";
        return power + "/" + toughness;
    }
}
