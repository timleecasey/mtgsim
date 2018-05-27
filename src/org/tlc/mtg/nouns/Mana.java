package org.tlc.mtg.nouns;

/**
 */
public enum  Mana {
  U {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Island");
    }
  },
  B {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Swamp");
    }
  },
  R {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Mountain");
    }
  },
  G {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Forrest");
    }
  },
  W {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Plains");
    }
  },
  X {
    @Override
    public boolean accepts(Card c) {
      return true;
    }
  };
  
  public boolean accepts(Card c) {
    return false;
  }
}
