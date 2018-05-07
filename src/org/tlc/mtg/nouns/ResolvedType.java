package org.tlc.mtg.nouns;

/**
 */
public enum ResolvedType {
    LAND,
    CRITTER,
    SPELL,
    INSTANT,
    ENCHANTMENT;

    public static ResolvedType resolve(String type) {
        String[] toks = type.split("\\s+");
        ResolvedType ret = null;

        for( String s : toks ) {
            switch (s) {
                case "Land":
                    ret = LAND;
                    break;
                case "Creature":
                    ret = CRITTER;
                    break;
            }
        }

        if( ret == null )
            throw new IllegalArgumentException("Could not resolve " + type);
        return ret;
    }
}
