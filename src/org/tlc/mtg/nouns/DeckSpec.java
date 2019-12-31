package org.tlc.mtg.nouns;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DeckSpec {
    public static class PlaceHolder {
        public int count;
        public String cardName;
        public Card card;

        public String toString() {
            return count + " " + cardName + ( card != null ? "" : "*");
        }
    }

    private List<PlaceHolder> cards = new ArrayList<PlaceHolder>();
    private int cardCount;

    public void addPlaceHolder(int n, String nm) {
        PlaceHolder p = new PlaceHolder();
        p.count = n;
        p.cardName = nm;
        cards.add(p);
        cardCount += n;
    }

    public String toString() {
        return cards.size() + " cards with " + cardCount + " card deck size";
    }

    public List<PlaceHolder> getCards() {
        return cards;
    }
}
