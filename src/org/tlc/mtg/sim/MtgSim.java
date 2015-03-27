package org.tlc.mtg.sim;

import org.tlc.mtg.io.CardIO;
import org.tlc.mtg.io.ResultsIO;
import org.tlc.mtg.nouns.DeckSpec;
import org.tlc.mtg.nouns.RawCard;

import java.util.List;
import java.util.Map;

/**
 */
public class MtgSim {
    private Map<String, RawCard> cards;
    private DeckSpec protoDeck;

    public MtgSim(Map<String, RawCard> cards, List<String> deckLines) {
        this.cards = cards;
    }

    public void parseToDeck() {

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
