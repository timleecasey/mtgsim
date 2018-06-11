package org.tlc.mtg.nouns.grammar;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.Phases;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 */
public class TextBinder {

  public static TextBinder binder = new TextBinder();

  List<TextToCardMods> mods = new LinkedList<>();

  static {
    TextToCardMods mod = new TextToCardMods(Pattern.compile("\\{T\\}: Add \\{(R|B|U|W|G|X)\\} to your mana pool."),
        new CardMod(Phases.PUT_INTO_PLAY, Function.<Card>identity()));
    binder.mods.add(mod);
  }

  public void bind(Card c) {
    for( TextToCardMods m : mods ) {
      m.accept(c);
    }
  }
}
