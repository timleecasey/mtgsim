package org.tlc.mtg.sim.player;

import java.util.List;

/**
 */
public class Game {
  private List<Player> players;
  private int cur;
  private PlayerPhases phases;

  public Game(PlayerPhases phases) {
    this.cur = 0;
    this.phases = phases;
  }

  public void addPlayer(Player p) {
    players.add(p);
  }

  public void start() {
    for (cur = 0; cur < players.size(); cur++) {
      Player p = players.get(0);
      phases.assignPhases(p);
      p.initialDraw();
    }
  }

  public void round() {
    for( cur=0 ; cur < players.size() ; cur++ ) {
      Player p = players.get(0);
      phases.turn(p);
    }
  }
}
