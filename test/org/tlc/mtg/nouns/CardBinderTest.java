package org.tlc.mtg.nouns;


import org.junit.Assert;
import org.junit.Test;
import org.tlc.mtg.sim.Stats;
import org.tlc.mtg.sim.player.Player;
import org.tlc.mtg.sim.player.PlayerPhases;

import java.util.Map;

public class CardBinderTest {

  @Test
  public void mustBind() throws Exception {
    Map m = SampleCards.elves();
    RawCard raw = new RawCard(m);
    CardBinder binder = new CardBinder();
    Card c = new Card();
    binder.bind(c, raw);

    Assert.assertTrue(c.animal.hasPwr);
    Assert.assertEquals(1, c.animal.power);
    Assert.assertEquals(1, c.animal.toughness);
    Assert.assertTrue(c.phases.containsKey(Phases.TAP));
    Assert.assertTrue(c.phases.containsKey(Phases.COMES_INTO_PLAY));
  }

  @Test
  public void mustCastWithElves() throws Exception {
    RawCard raw;
    Map m;
    CardBinder binder = new CardBinder();

    m = SampleCards.elves();
    raw = new RawCard(m);
    Card elves = new Card();
    binder.bind(elves, raw);

    m = SampleCards.forrest();
    raw = new RawCard(m);
    Card forrest = new Card();
    binder.bind(forrest, raw);

    m = SampleCards.bears();
    raw = new RawCard(m);
    Card bears = new Card();
    binder.bind(bears, raw);

    Stats[] stats = new Stats[1];
    stats[0] = new Stats();
    Player p = new Player(stats);


    p.getPhases().clear();
    p.getPhases().add(new PlayerPhases.Untap());
    p.getPhases().add(new PlayerPhases.Upkeep());
    p.getPhases().add(new PlayerPhases.Draw());
    p.getPhases().add(new PlayerPhases.Land());
    p.getPhases().add(new PlayerPhases.Spells());
    p.getPhases().add(new PlayerPhases.Attack());
    p.getPhases().add(new PlayerPhases.CleanUp());
    p.getPhases().add(new PlayerPhases.End());

    p.placeLand(forrest);
    new PlayerPhases.Untap().func.apply(p);

    p.getHand().add(elves);

    p.castSpell(elves);
    new PlayerPhases.Untap().func.apply(p);

    p.getHand().add(bears);
    p.castSpell(bears);

    Assert.assertEquals(2, p.getBoard().getCritters().depth());
  }



}