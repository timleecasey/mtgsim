package org.tlc.mtg.sim;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.DeckSpec;

import java.util.List;

/**
 */
public class DeckGenerator {
    private DeckSpec orig;
    private List<Card> cur;
    private List<Card> src;

    /**
     * spec -> src
     */
    protected void populate() {
        for( DeckSpec.PlaceHolder ph : orig.getCards() ) {
            for( int i= 0 ; i < ph.count ; i++ ) {
                src.add(Card.makeCopy(ph.card));
            }
        }
    }
}
