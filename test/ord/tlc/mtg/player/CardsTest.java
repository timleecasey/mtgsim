package ord.tlc.mtg.player;

import org.junit.Assert;
import org.junit.Test;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.sim.player.Cards;

import java.util.HashSet;
import java.util.Set;

/**
 */
public class CardsTest {

  @Test
  public void testShuffle() {

    Cards cards = new Cards();

    for( int i=0 ; i < 10 ; i++ ) {
      cards.add(genCard("" + i));
    }
    cards.shuffle();

    Assert.assertEquals("even", 10, cards.depth());
    Set<String> names = new HashSet<>();
    cards.visit(c -> names.add(c.name));
    Assert.assertEquals("even names", 10, names.size());
    names.clear();

    cards = new Cards();

    for( int i=0 ; i < 11 ; i++ ) {
      cards.add(genCard("" + i));
    }
    cards.shuffle();

    Assert.assertEquals("odd", 11, cards.depth());
    cards.visit(c -> names.add(c.name));
    Assert.assertEquals("odd names", 11, names.size());
    names.clear();

  }

  public Card genCard(String nm) {
    Card c = new Card();
    c.name = nm;
    return c;
  }
}
