package org.tlc.mtg.sim;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.DeckSpec;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DeckGenerator {
    private DeckSpec orig;
    private List<Card> src;

    public DeckGenerator(DeckSpec orig) {
        this.orig = orig;
    }

    /**
     * spec -> src
     */
    protected void populate() {
        src = new ArrayList<>();
        for( DeckSpec.PlaceHolder ph : orig.getCards() ) {
            for( int i= 0 ; i < ph.count ; i++ ) {
                src.add(Card.makeCopy(ph.card));
            }
        }
    }

    public List<Card> getSrc() {
        return src;
    }
}
