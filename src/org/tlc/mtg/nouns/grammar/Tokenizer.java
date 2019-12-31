package org.tlc.mtg.nouns.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Tokenizer {
  public static class Token {
    byte type;
    String base;

    public Token(String base) {
      this.base = base;
    }
  }

  public Token[] tokenize(String s) {
    String[] sa = baseSplit(s);
    List<Token> l = new ArrayList<>(100);
    for( String base : sa ) {
      Token t = new Token(base);
      l.add(t);
    }

    return (Token[]) l.toArray();
  }

  public static String[] baseSplit(String s) {
    return s.split("\\s+");
  }
}
