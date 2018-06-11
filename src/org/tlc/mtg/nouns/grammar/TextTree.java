package org.tlc.mtg.nouns.grammar;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class TextTree {

  public static class TextNode {
    String tok;
    Map<TextNode, TextNode> children = new HashMap<>();

    public TextNode(String tok) {
      this.tok = tok;
    }

    public TextNode get(String s) {
      return children.get(new TextNode(s));
    }
    public void put(TextNode child) {
      children.put(child, child);
    }

    @Override
    public String toString() {
      return tok;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      TextNode textNode = (TextNode) o;

      if (!tok.equals(textNode.tok)) { return false; }

      return true;
    }

    @Override
    public int hashCode() {
      return tok.hashCode();
    }
  }

  private static TextNode root = new TextNode("");
  public static void addTextStr(String base) {
    String[] sa = Tokenizer.baseSplit(base);
    TextNode cur = root;
    for( String s : sa ) {
      s = TokenInternment.intern(s);
      TextNode tn = cur.get(s);
      if( tn == null ) {
        tn = new TextNode(s);
        cur.put(tn);
      }
      cur = tn;
    }
  }
}
