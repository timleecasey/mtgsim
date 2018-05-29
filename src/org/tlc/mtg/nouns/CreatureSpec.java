package org.tlc.mtg.nouns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

  void bind(RawCard raw) {
    String pStr = (String) raw.getFieldByName("power");
    String tStr = (String) raw.getFieldByName("toughness");

    if( pStr == null || tStr == null ) {
      hasPwr = false;
      return;
    }

    hasPwr = true;
    power = Integer.valueOf(pStr);
    toughness = Integer.valueOf(tStr);

    String text = (String) raw.getFieldByName("text");
    flying = text.toLowerCase().contains("flying");
    vigilance = text.toLowerCase().contains("vigilance");
    firstStrike = text.toLowerCase().contains("first strike");
    doubleStrike = text.toLowerCase().contains("double strike");
    defender = text.toLowerCase().contains("defender");
    heroic = text.toLowerCase().contains("heroic");
    inspired = text.toLowerCase().contains("inspired");
    lifelink = text.toLowerCase().contains("lifelink");

    List<String> typeList = (List) raw.getFieldByName("types");
    List<String> subTypeList = (List) raw.getFieldByName("subtypes");
    Set<String> subTypes = new HashSet<>(subTypeList);
    if( typeList != null ) {
      for( String t : typeList ) {
        types.put(t, subTypes);
      }
    }
  }

  @Override
  public String toString() {
    if( ! hasPwr )
      return "";
    return power + "/" + toughness;
  }
}
