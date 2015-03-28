package org.tlc.mtg.sim;

import org.tlc.mtg.io.CardIO;
import org.tlc.mtg.io.ResultsIO;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.DeckSpec;
import org.tlc.mtg.nouns.RawCard;

import java.util.List;
import java.util.Map;

/**
 */
public class MtgSim {
    private Map<String, RawCard> cards;
    private List<String> deckLines;
    private DeckSpec protoDeck;

    public MtgSim(Map<String, RawCard> cards, List<String> deckLines) {
        this.cards = cards;
        this.deckLines = deckLines;
        protoDeck = new DeckSpec();
    }

    public void parseToDeck() {
        for( String ln : deckLines) {

            int sp = ln.indexOf(' ');

            int cnt = Integer.valueOf(ln.substring(0, sp));
            if( cnt < 1 ) {
                throw new IllegalArgumentException("Could not find a space to resolve # Name " + ln);
            }

            String nm = ln.substring(sp + 1).trim();

            protoDeck.addPlaceHolder(cnt, nm);
        }

        for(DeckSpec.PlaceHolder ph : protoDeck.getCards() ) {
            RawCard rc = cards.get(ph.cardName);
            if( rc == null ) {
                throw new NullPointerException("Could not find card named " + ph.cardName);
            }

            Card c = new Card();
            c.bind(rc);

            ph.card = c;
        }

    }

    public void simulate() {

    }

    //
    // Damage, frequency
    //
    public Map<Integer, Integer> results() {
        return null;
    }

    public static void main(String[] av) throws Exception {
        Map<String, RawCard> cards = CardIO.read(av[0]);
        List<String> cardLines = CardIO.readCardLines(av[1]);


        MtgSim sim = new MtgSim(cards, cardLines);
        sim.parseToDeck();
        sim.simulate();
        ResultsIO.writeResults(sim.results());
    }
}
