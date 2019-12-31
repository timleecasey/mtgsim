package org.tlc.mtg.sim.player;

import org.junit.Assert;
import org.junit.Test;
import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.CardBinder;
import org.tlc.mtg.nouns.RawCard;
import org.tlc.mtg.nouns.SampleCards;
import org.tlc.mtg.nouns.cards.Tap;
import org.tlc.mtg.sim.Stats;

public class UntapTest {

  @Test
  public void mustUntap() {
    RawCard m = new RawCard(SampleCards.forrest());
    Card forrest = new Card();
    new CardBinder().bind(forrest, m);

    Stats[] stats = new Stats[1];
    stats[0] = new Stats();
    Player p = new Player(stats);

    p.placeLand(forrest);

    new Tap().func.apply(forrest);

    Assert.assertTrue(forrest.tapped);

    new PlayerPhases.Untap().func.apply(p);

    Assert.assertFalse(forrest.tapped);
  }

  @Test
  public void mustClearSummoning() {
    CardBinder binder = new CardBinder();
    Card forrest = new Card();
    Card elves = new Card();
    binder.bind(forrest, new RawCard(SampleCards.forrest()));
    binder.bind(elves, new RawCard(SampleCards.elves()));

    Stats[] stats = new Stats[1];
    stats[0] = new Stats();
    Player p = new Player(stats);

    p.placeLand(forrest);
    new PlayerPhases.Untap().func.apply(p);

    p.getHand().add(elves);

    p.castSpell(elves);
    Assert.assertTrue(elves.animal.sick);
    Assert.assertFalse(elves.tapped);

    new PlayerPhases.Untap().func.apply(p);
    Assert.assertFalse(elves.animal.sick);
    Assert.assertFalse(elves.tapped);


  }

}