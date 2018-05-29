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

    @Override
    public Mana join(Mana next) {
      switch( next ) {
        case W:
          return RW;
      }
      throw new IllegalArgumentException("Could not join" + this + " to " + next);
    }
  },
  RW {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Mountain") || c.name.equals("Plains");
    }
  },
  G {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Forrest");
    }

    @Override
    public Mana join(Mana next) {
      switch( next ) {
        case W:
          return GW;
      }
      throw new IllegalArgumentException("Could not join" + this + " to " + next);
    }
  },
  GW {
    @Override
    public boolean accepts(Card c) {
      return c.name.equals("Forrest") || c.name.equals("Plains");
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

  public Mana join(Mana first) {
    return X;
  }

  public static Mana fromColorChar(char c) {
    switch( c ) {
      case 'U':
        return Mana.U;
      case 'B':
        return Mana.B;
      case 'R':
        return Mana.R;
      case 'G':
        return Mana.G;
      case 'W':
        return Mana.W;
    }

    return X;
  }

}
