package org.tlc.mtg.sim;

import org.tlc.mtg.io.CardIO;
import org.tlc.mtg.io.ResultsIO;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.DeckSpec;
import org.tlc.mtg.nouns.RawCard;
import org.tlc.mtg.sim.player.PlayerTurnPhases;
import org.tlc.mtg.util.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class MtgSim {
    private Map<String, RawCard> cardDict;
    private List<String> deckLines;
    private DeckSpec protoDeck;
    private Map<Integer, Counter> damageFreqs = new TreeMap<>();

    public MtgSim(Map<String, RawCard> cardDict, List<String> deckLines) {
        this.cardDict = cardDict;
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
            RawCard rc = cardDict.get(ph.cardName);
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
        final Map<Long, Counter> damageFreqs = new HashMap<>();
        final Counter counter = new Counter();
        final Counter constrained = new Counter();
        final PlayerTurnPhases ptp = new PlayerTurnPhases();

        PermuteArray<Card> permute = new PermuteArray<>(gen.getSrc());
        permute.visit(new PermuteArray.PermuteListVisitor<Card>() {
            @Override
            public void visit(List<Card> cur) {
              counter.inc();
              ptp.assignDeck(cur);
              ptp.playout();
              if( ptp.getPlayer().isConstrained() ) {
                constrained.inc();
              }
              if( counter.getValue() % 1000000 == 0 ) {
                System.out.println(constrained.getValue() + " / " + counter.getValue());
              }
//                DamageCalc dam = new DamageCalc(cur);
//                long n = dam.damage();
//                Counter c = damageFreqs.get(n);
//                if( c == null ) {
//                    c = new Counter();
//                    damageFreqs.put(n, c);
//                }
//                c.inc();
//                counter.inc();
//
            }
        });

        for( long n : damageFreqs.keySet() ) {
            System.out.println(n + ":" + damageFreqs.get(n));
        }
        System.out.println(counter.getValue() + " runs");
    }

    //
    // Damage, frequency
    //
    public Map<Integer, Counter> results() {
        return damageFreqs;
    }

    public static void main(String[] av) throws Exception {
      System.out.println("Cards...");
        Map<String, RawCard> cards = CardIO.read(av[0]);
      System.out.println("Decks...");
      List<String> cardLines = CardIO.readCardLines(av[1]);


        MtgSim sim = new MtgSim(cards, cardLines);
        sim.parseToDeck();
        System.out.println("Simulation...");
        sim.simulate();
        ResultsIO.writeResults(sim.results());
    }
}
