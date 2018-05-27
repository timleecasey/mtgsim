package org.tlc.mtg.nouns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public class CostSpec {
  public String mana;
  public boolean hasCost;
  public Mana[] slots;

  void bind(RawCard raw) {

    String rawCost = ((String)raw.getFieldByName("manaCost"));
    if( rawCost != null )
      rawCost = rawCost.replaceAll("[\\{\\}]", "");
    this.mana = rawCost;
    if( this.mana == null ) {
      hasCost = false;
    } else {
      hasCost = true;
      buildCost();
    }
  }

  protected void buildCost() {
    List<Mana> l = new ArrayList<>();
    char ca[] = mana.toCharArray();
    for( int at=0 ; at < ca.length ; at++ ) {
      char c = ca[at];
      switch( c ) {
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          int len =  c - '0';
          //
          // Lets hope this covers it....
          //
          if( at < ca.length - 1 && Character.isDigit(ca[at+1])) {
            len *= 10;
          }
          for( int i=0 ; i < len ; i++ ) {
            l.add(Mana.X);
          }
          break;
        case 'U':
          l.add(Mana.U);
          break;
        case 'B':
          l.add(Mana.B);
          break;
        case 'R':
          l.add(Mana.R);
          break;
        case 'G':
          l.add(Mana.G);
          break;
        case 'W':
          l.add(Mana.W);
          break;
      }
      slots = new Mana[l.size()];

      //
      // Make the colorless at the end
      //
      Collections.reverse(l);

      int i=0;
      for( Mana m : l ) {
        slots[i++] = m;
      }
    }
  }

  @Override
  public String toString() {
    if( hasCost )
      return mana;
    else
      return "";
  }
}
