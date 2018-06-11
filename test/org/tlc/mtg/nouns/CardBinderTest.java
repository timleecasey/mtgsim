package org.tlc.mtg.nouns;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CardBinderTest {

  @Test
  public void mustBind() throws Exception {
    Map m = Elves.elves();
    RawCard raw = new RawCard(m);
    CardBinder binder = new CardBinder();
    Card c = new Card();
    binder.bind(c, raw);

    Assert.assertTrue(c.animal.hasPwr);
    Assert.assertEquals(1, c.animal.power);
    Assert.assertEquals(1, c.animal.toughness);
    Assert.assertTrue(c.phases.containsKey(Phases.TAP));
  }



}