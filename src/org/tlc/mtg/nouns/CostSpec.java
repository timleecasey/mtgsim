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
    this.mana = rawCost;
    if( this.mana == null ) {
      hasCost = false;
    } else {
      hasCost = true;
      buildCost();
    }
  }

  protected void buildCost() {
    Mana m;
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
          int len = c - '0';
          //
          // Lets hope this covers it....
          //
          if (at < ca.length - 1 && Character.isDigit(ca[at + 1])) {
            len *= 10;
          }
          for (int i = 0; i < len; i++) {
            l.add(Mana.X);
          }
          break;
        case '/':
          Mana m1 = l.remove(l.size() - 1);
          Mana m2 = Mana.fromColorChar(ca[at+1]);
          m = m1.join(m2);
          l.add(m);
          at++;
          break;
        case '{':
        case '}':
          break;
        default:
          m = Mana.fromColorChar(c);
          l.add(m);
          break;
      }

    }

    slots = new Mana[l.size()];

    //
    // Make the colorless at the end
    //
    Collections.reverse(l);

    int i=0;
    for( Mana m2 : l ) {
      slots[i++] = m2;
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
