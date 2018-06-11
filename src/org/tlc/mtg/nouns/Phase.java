package org.tlc.mtg.nouns;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 */
public class Phase<T> {
  public Set<Phases> phases = new HashSet<>();
  public Function<T, T> func;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for( Phases n : phases) {
      if( sb.length() > 0 ) {
        sb.append(", ");
      }
      sb.append(n.toString());
    }
    return sb.toString();
  }
}
