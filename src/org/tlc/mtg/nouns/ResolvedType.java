package org.tlc.mtg.nouns;

import org.tlc.mtg.sim.player.Board;

/**
 */
public enum ResolvedType {
  WALKER {
    @Override
    public void play(Board b, Card c) {
      b.getPlanesWalkers().add(c);
      if( c.animal.hasPwr ) {
        c.animal.sick = true;
      }
    }

    @Override
    public void clearSummoning(Card c) {
      if( c.animal.hasPwr ) {
        c.animal.sick = false;
      }
    }
  },
  ARTIFACT {
    @Override
    public void play(Board b, Card c) {
      b.getArtifacts().add(c);
      if( c.animal.hasPwr ) {
        c.animal.sick = true;
      }
    }

    @Override
    public void clearSummoning(Card c) {
      if( c.animal.hasPwr ) {
        c.animal.sick = false;
      }
    }
  },
  LAND {
    @Override
    public void play(Board b, Card c) {
      b.getLand().add(c);
    }
  },
  CRITTER {
    @Override
    public void play(Board b, Card c) {
      b.getCritters().add(c);
      if( c.animal.hasPwr ) {
        c.animal.sick = true;
      }
    }

    @Override
    public void clearSummoning(Card c) {
      if( c.animal.hasPwr ) {
        c.animal.sick = false;
      }
    }
  },
  SPELL {
    @Override
    public void play(Board b, Card c) {
      b.getGrave().add(c);
    }
  },
  INSTANT {
    @Override
    public void play(Board b, Card c) {
      b.getGrave().add(c);
    }
  },
  ENCHANTMENT {
    @Override
    public void play(Board b, Card c) {
      b.getEnchantments().add(c);
    }
  };

  public void play(Board b, Card c)
    { }

  public void clearSummoning(Card c)
    { }

  public static ResolvedType resolve(String type) {
    String[] toks = type.split("\\s+");
    ResolvedType ret = null;

    for( String s : toks ) {
      switch (s) {
        case "Land":
          ret = LAND;
          break;
        case "Creature":
          ret = CRITTER;
          break;
        case "Enchantment":
          ret = ENCHANTMENT;
          break;
        case "Planeswalker":
          ret = WALKER;
          break;
        case "Artifact":
          ret = ARTIFACT;
          break;
        case "Instant":
          ret = INSTANT;
          break;
      }
    }

    if( ret == null )
      throw new IllegalArgumentException("Could not resolve " + type);
    return ret;
  }
}
