package org.tlc.mtg.nouns;

/**
 */
public class CostSpec {
  public String mana;
  public boolean hasCost;
  public Mana[] slots;

  @Override
  public String toString() {
    if( hasCost )
      return mana;
    else
      return "";
  }
}
