package org.tlc.mtg.sim;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.sim.player.Player;
import org.tlc.mtg.sim.player.PlayerPhases;
import org.tlc.mtg.util.Counter;

import java.util.List;

/**
*/
public class PlayerSimulator implements PermuteArray.PermuteListVisitor<Card> {
  private PlayerPhases pp;
  private Player player;
  private Counter counter;
  private Counter constrained;
  public PlayerSimulator(Player player, Counter counter, Counter constrained) {
    this.pp = new PlayerPhases();
    this.player = player;
    this.counter = counter;
    this.constrained = constrained;
  }

  public void visit(List<Card> cur) {
    counter.inc();
    pp.assignDeck(player, cur);
    pp.assignPhases(player);
    pp.playout(player);
    if (player.isConstrained()) {
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
