package org.tlc.mtg.sim;

import org.tlc.mtg.io.CardIO;
import org.tlc.mtg.io.ResultsIO;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.CardBinder;
import org.tlc.mtg.nouns.DeckSpec;
import org.tlc.mtg.nouns.RawCard;
import org.tlc.mtg.sim.player.Player;
import org.tlc.mtg.sim.player.PlayerPhases;
import org.tlc.mtg.util.Counter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class MtgSim {
  private Map<String, RawCard> cardDict;
  private ProtoPlayer proto;

  public MtgSim(Map<String, RawCard> cardDict, ProtoPlayer pp) {
    this.cardDict = cardDict;
    this.proto = pp;
  }

  protected boolean isOnlyDigits(String s) {
    for( char c : s.toCharArray() ) {
      if( ! Character.isDigit(c) ) {
        return false;
      }
    }
    return true;
  }

  public void parseToDeck() {
    for( String ln : proto.getDeckLines()) {

      int sp = ln.indexOf(' ');
      String cntStr = ln.substring(0, sp);

      if( isOnlyDigits(cntStr) ) {
        int cnt = Integer.valueOf(cntStr);
        if (cnt < 1) {
          throw new IllegalArgumentException("Could not find a space to resolve # Name " + ln);
        }

        String nm = ln.substring(sp + 1).trim();

        proto.getProtoDeck().addPlaceHolder(cnt, nm);
      } else {
        proto.getProtoDeck().addPlaceHolder(1, ln);
      }
    }

    CardBinder binder = new CardBinder();

    for(DeckSpec.PlaceHolder ph : proto.getProtoDeck().getCards() ) {
      RawCard rc = cardDict.get(ph.cardName);
      if( rc == null ) {
        throw new NullPointerException("Could not find card named " + ph.cardName);
      }

      Card c = new Card();
      c.bind(rc, binder);

      ph.card = c;
    }

  }

  public void simulate() {
    DeckGenerator gen = new DeckGenerator(proto.getProtoDeck());
    gen.populate();
    Counter counter = new Counter();
    Counter constrained = new Counter();
    List<Card> cards = gen.getSrc();
    Stats[] stats = Stats.genStats(cards.size());
    Player player = new Player(stats);
    PermuteArray<Card> permute = new PermuteArray<>(cards);

    PlayerSimulator sim =  new PlayerSimulator(player, counter, constrained);
    permute.trials(sim);

    System.out.println("id,constrained," + constrained.getValue() + " / " + counter.getValue());
    System.out.println("id,total," + counter.getValue());
    ResultsIO.writeResults("id", stats);
  }

  //
  // Damage, frequency
  //
  public Map<Integer, Counter> results() {
    return proto.getDamageFreqs();
  }

  public static void main(String[] av) throws Exception {
    System.out.println("Cards...");
    Map<String, RawCard> cards = CardIO.read(av[0]);
    System.out.println("Read " + cards.size() + " total cards.");
    System.out.println("Decks...");
    List<String> cardLines = CardIO.readCardLines(av[1]);
    System.out.println("Deck contains " + cardLines.size() + " lines with " + sizeDeck(cardLines) + " cards.");

    ProtoPlayer pp = new ProtoPlayer(cardLines, new DeckSpec());
    MtgSim sim = new MtgSim(cards, pp);
    sim.parseToDeck();
    System.out.println("Simulation...");
    sim.simulate();
  }

  protected static int sizeDeck(List<String> cards) {
    int total = 0;
    for( String ln : cards ) {
      if( ln.length() == 0 ) {
        continue;
      }
      if( ln.startsWith("#") ) {
        continue;
      }
      if( Character.isDigit(ln.charAt(0)) ) {
        String[] parts = ln.split("\\s+");
        int n = Integer.parseInt(parts[0]);
        total += n;
      } else {
        total++;
      }
    }
    return total;
  }

  public static class ProtoPlayer {
    private List<String> deckLines;
    private DeckSpec protoDeck;
    private Map<Integer, Counter> damageFreqs = new TreeMap<>();

    public ProtoPlayer(List<String> deckLines, DeckSpec protoDeck) {
      this.deckLines = deckLines;
      this.protoDeck = protoDeck;
    }

    public List<String> getDeckLines() {
      return deckLines;
    }

    public DeckSpec getProtoDeck() {
      return protoDeck;
    }

    public Map<Integer, Counter> getDamageFreqs() {
      return damageFreqs;
    }
  }
}
