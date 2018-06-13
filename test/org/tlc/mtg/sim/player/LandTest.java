package org.tlc.mtg.sim.player;

import org.junit.Assert;
import org.junit.Test;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.CardBinder;
import org.tlc.mtg.nouns.RawCard;
import org.tlc.mtg.nouns.SampleCards;
import org.tlc.mtg.sim.Stats;

import java.util.ArrayList;
import java.util.List;

public class LandTest {

  @Test
  public void dropLandFromHand() {
    List<Card> srcCards = new ArrayList<>();
    CardBinder binder = new CardBinder();
    for( int i=0 ; i < 10 ; i++ ) {
      Card forrest = new Card();
      binder.bind(forrest, new RawCard(SampleCards.forrest()));
      srcCards.add(forrest);
    }

    Stats[] stats = new Stats[1];
    stats[0] = new Stats();
    Player p = new Player(stats);
    p.resetPlayer(10);

    p.getDeck().resetAndAddAllCards(srcCards);
    Assert.assertEquals(0, stats[0].discard);
    Assert.assertEquals(10, p.getDeck().depth());

    p.initialDraw();
    Assert.assertEquals(3, p.getDeck().depth());
    Assert.assertEquals(7, p.getHand().depth());

    new PlayerPhases.Draw().func.apply(p);
    Assert.assertEquals(2, p.getDeck().depth());
    Assert.assertEquals(8, p.getHand().depth());

    new PlayerPhases.Land().func.apply(p);
    Assert.assertEquals(1, p.getBoard().getLand().depth());
    Assert.assertEquals(2, p.getDeck().depth());
    Assert.assertEquals(7, p.getHand().depth());

    Assert.assertEquals(1, stats[0].land);

  }

}