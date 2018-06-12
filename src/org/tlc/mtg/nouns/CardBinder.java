package org.tlc.mtg.nouns;

import org.tlc.mtg.nouns.cards.Identity;
import org.tlc.mtg.nouns.grammar.TextBinder;
import org.tlc.mtg.nouns.grammar.TextTree;
import org.tlc.mtg.nouns.grammar.TokenInternment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Go from a raw card, read in from a source of cards like json file, into a parsed card.
 * This will pick up text and modify the card for the game.
 */
public class CardBinder {
  public void bind(Card c, RawCard raw) {
    c.buildStages();
    bindBase(c, raw);
    bindCost(c, raw);
    bindAnimal(c, raw);

    if( c.animal.vigilance ) {
      c.phases.put(Phases.ATTACK, new Identity(Phases.ATTACK));
    }
    if( c.animal.defender ) {
      c.phases.remove(Phases.ATTACK);
    }

    parseCard(c);

  }

  private void parseCard(Card c) {
    TextBinder binder = TextBinder.binder;
    binder.bind(c);
  }

  private void bindBase(Card c, RawCard raw) {
    c.name = raw.getName();
    c.type = raw.getType();
    c.text = (String) raw.getFieldByName("text");
    c.resType = ResolvedType.resolve(c.type);
  }

  private void bindCost(Card c, RawCard raw) {
    c.cost.mana = ((String)raw.getFieldByName("manaCost"));
    if( c.cost.mana == null ) {
      c.cost.hasCost = false;
    } else {
      c.cost.hasCost = true;
      buildCost(c);
    }

  }

  private void buildCost(Card card) {
    Mana m;
    List<Mana> l = new ArrayList<>();
    char ca[] = card.cost.mana.toCharArray();
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

    card.cost.slots = new Mana[l.size()];

    //
    // Make the colorless at the end
    //
    Collections.reverse(l);

    int i=0;
    for( Mana m2 : l ) {
      card.cost.slots[i++] = m2;
    }

  }

  private void bindAnimal(Card c, RawCard raw) {
    String pStr = (String) raw.getFieldByName("power");
    String tStr = (String) raw.getFieldByName("toughness");

    if( pStr == null || tStr == null ) {
      c.animal.hasPwr = false;
      return;
    }

    c.animal.hasPwr = true;
    c.animal.power = Integer.valueOf(pStr);
    c.animal.toughness = Integer.valueOf(tStr);

    if( c.text != null ) {
      c.animal.flying = c.text.toLowerCase().contains("flying");
      c.animal.vigilance = c.text.toLowerCase().contains("vigilance");
      c.animal.firstStrike = c.text.toLowerCase().contains("first strike");
      c.animal.doubleStrike = c.text.toLowerCase().contains("double strike");
      c.animal.defender = c.text.toLowerCase().contains("defender");
      c.animal.heroic = c.text.toLowerCase().contains("heroic");
      c.animal.inspired = c.text.toLowerCase().contains("inspired");
      c.animal.lifelink = c.text.toLowerCase().contains("lifelink");
    }

    @SuppressWarnings("unchecked") List<String> typeList = (List) raw.getFieldByName("types");
    @SuppressWarnings("unchecked") List<String> subTypeList = (List) raw.getFieldByName("subtypes");
    Set<String> subTypes = new HashSet<>(subTypeList);
    if( typeList != null ) {
      for( String t : typeList ) {
        c.animal.types.put(t, subTypes);
      }
    }

  }
}
