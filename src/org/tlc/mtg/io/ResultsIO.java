package org.tlc.mtg.io;

import org.tlc.mtg.sim.Stats;
import org.tlc.mtg.util.Counter;

import java.io.IOException;
import java.util.Map;

/**
 */
public class ResultsIO {

    public static void writeResults(String simId, Stats[] stats) {
      System.out.print(simId);
      System.out.print(",");
      System.out.print("TURN");
      System.out.print(",");
      System.out.print("DISCARD");
      System.out.print(",");
      System.out.print("LAND");
      System.out.print(",");
      System.out.print("CREATURE");
      System.out.println("");
      int turn = 0;
      for( Stats s : stats ) {
        System.out.print(simId);
        System.out.print(",");
        System.out.print(turn++);
        System.out.print(",");
        System.out.print(s.discard);
        System.out.print(",");
        System.out.print(s.land);
        System.out.print(",");
        System.out.print(s.critter);
        System.out.println("");
      }
    }
}
