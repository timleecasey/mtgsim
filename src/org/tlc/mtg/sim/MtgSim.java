package org.tlc.mtg.sim;

import org.tlc.mtg.io.CardIO;
import org.tlc.mtg.io.ResultsIO;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.DeckSpec;
import org.tlc.mtg.nouns.RawCard;
import org.tlc.mtg.util.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class MtgSim {
    private Map<String, RawCard> cards;
    private List<String> deckLines;
    private DeckSpec protoDeck;
    private Map<Integer, Counter> damageFreqs = new HashMap<>();

    public MtgSim(Map<String, RawCard> cards, List<String> deckLines) {
        this.cards = cards;
        this.deckLines = deckLines;
        protoDeck = new DeckSpec();
        damageFreqs = new HashMap<>();
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
        DeckGenerator gen = new DeckGenerator(protoDeck);
        gen.populate();
        final Map<Integer, Counter> damageFreqs = new HashMap<>();

        PermuteArray<Card> permute = new PermuteArray<>(gen.getSrc());
        permute.visit(new PermuteArray.PermuteListVisitor<Card>() {
            @Override
            public void visit(List<Card> cur) {
                DamageCalc dam = new DamageCalc(cur);
                int n = dam.damage();
                Counter c = damageFreqs.get(n);
                if( c == null ) {
                    c = new Counter();
                    damageFreqs.put(n, c);
                }
                c.inc();
            }
        });
    }

    //
    // Damage, frequency
    //
    public Map<Integer, Counter> results() {
        return damageFreqs;
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
