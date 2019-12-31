package org.tlc.mtg.sim;

/**
 */
public class Stats {
  public long discard;
  public long land;
  public long critter;

  @Override
  public String toString() {
    return "D: " +  discard +
          " L: " + land +
          " C: " + critter
        ;
  }

  public static Stats[] genStats(int len) {
    Stats[] ret = new Stats[len];
    for( int i=0 ; i < len ; i++) {
      ret[i] = new Stats();
    }
    return ret;
  }
}
