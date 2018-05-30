package org.tlc.mtg.sim;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.sim.player.PlayerPhases;
import org.tlc.mtg.util.Counter;

import java.util.List;

/**
*/
public class PlayerSimulator implements PermuteArray.PermuteListVisitor<Card> {
  private PlayerPhases pp;
  private Counter counter;
  private Counter constrained;
  public PlayerSimulator(PlayerPhases pp, Counter counter, Counter constrained) {
    this.pp = pp;
    this.counter = counter;
    this.constrained = constrained;
  }

  public void visit(List<Card> cur) {
    counter.inc();
    pp.assignDeck(cur);
    pp.playout();
    if (pp.getPlayer().isConstrained()) {
      constrained.inc();
    }
  }

  public PlayerPhases getPhases() {
    return pp;
  }

  public Counter getCounter() {
    return counter;
  }

  public Counter getConstrained() {
    return constrained;
  }
}
