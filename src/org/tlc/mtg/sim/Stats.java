package org.tlc.mtg.sim;

/**
 */
public class Stats {
  public long discard;
  public long land;
  public long critter;

  public static Stats cur = null;
  public static Stats[] src;

  @Override
  public String toString() {
    return "D: " +  discard +
          " L: " + land +
          " C: " + critter
        ;
  }
}
