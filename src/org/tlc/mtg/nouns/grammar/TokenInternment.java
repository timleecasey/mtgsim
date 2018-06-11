package org.tlc.mtg.nouns.grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 */
public class TokenInternment {
  private static Map<String, String> strs = new HashMap<>();
  public static String intern(String s) {
    String ret = strs.get(s);
    if( ret == null ) {
      strs.put(s, s);
      return s;
    }
    return ret;
  }

  private static Set<String> names = new HashSet<>();
  public static void addName(String s) {
    s = intern(s);
    names.add(s);
  }
  public static boolean isName(String s) {
    s = intern(s);
    return names.contains(s);
  }

  public static void addNameStr(String base) {
    String[] sa = Tokenizer.baseSplit(base);
    for( String s : sa ) {
      addName(s);
    }
  }
}
