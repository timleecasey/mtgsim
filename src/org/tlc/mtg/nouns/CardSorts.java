package org.tlc.mtg.nouns;

import java.util.Comparator;

/**
 */
public class CardSorts {
  public static class HiToLowDamageSort implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
      if( o1.resType.equals(o2.resType) ) {
        if( o1.resType.equals(ResolvedType.CRITTER) && o2.resType.equals(ResolvedType.CRITTER) ) {
          return o1.animal.power - o2.animal.power;
        }
        return 0;
      } else {
        return o1.resType.ordinal() - o2.resType.ordinal();
      }
    }
  }
}
