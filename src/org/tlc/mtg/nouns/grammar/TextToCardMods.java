package org.tlc.mtg.nouns.grammar;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Mana;
import org.tlc.mtg.nouns.Phase;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class TextToCardMods {
  /**
   * Regex for selecting this card
   */
  private Pattern trigger;
  /**
   * How each phase will be modified for the given trigger.
   */
  private CardMod[] mods;

  public TextToCardMods(Pattern trigger, CardMod... mods) {
    this.trigger = trigger;
    this.mods = mods;
  }

  public boolean accept(Card c) {
    if( c.text == null ) {
      return false;
    }
    Matcher m = trigger.matcher(c.text);
    if( m.matches() ) {
      if( m.groupCount() > 0 ) {
        convertMana(c, m, 1);
      }
      for( CardMod mod : mods ) {
        Phase<Card> phase = c.phases.get(mod.phase);
        if( phase == null ) {
          phase = new Phase<Card>();
          phase.func = mod.func;
          c.phases.put(mod.phase, phase);
          continue;
        }
        Function<Card, Card> func = phase.func;
        Function<Card, Card> modFunc = mod.func;
        phase.func =  modFunc.<Card>andThen(func);
      }
      return true;
    }
    return false;
  }

  public void convertMana(Card c, Matcher m, int group) {
    String s = m.group(group);
    if( s != null && s.length() > 0 ) {
      Mana mana = Mana.fromColorChar(s.charAt(0));
      c.manaSrc = new Mana[] {mana};
    }
  }
}
