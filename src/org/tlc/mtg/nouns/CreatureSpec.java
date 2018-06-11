package org.tlc.mtg.nouns;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 */
public class CreatureSpec {
  public int power;
  public int toughness;
  /**
   * The current accrued damage.
   */
  public int damage;
  public boolean hasPwr;
  public boolean sick;
  public boolean flying;
  public boolean firstStrike;
  public boolean doubleStrike;
  public boolean vigilance;
  public boolean defender;
  public boolean heroic;
  public boolean inspired;
  public boolean lifelink;

  public Map<String, Set<String>> types = new HashMap<>();

  @Override
  public String toString() {
    if( ! hasPwr )
      return "";
    return power + "/" + toughness;
  }
}
