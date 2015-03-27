package org.tlc.mtg.nouns;

import java.util.Map;

/**
 */
public class RawCard {
    private String name;
    private String type;
    private Map<String, ?> raw;

    public RawCard(Map<String, ?> raw) {
        this.raw = raw;
        setName((String) raw.get("name"));
        setType((String) raw.get("type"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + ": " + type;
    }
}
