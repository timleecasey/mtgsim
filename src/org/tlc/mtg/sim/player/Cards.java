package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
public abstract class Cards {
  protected List<Card> cards = new ArrayList<>();
  public interface CardVisitor {
    public void visit(Card c);
  }

  public abstract void add(Card c);
  public abstract Card remove();

  public int depth() {
    return cards.size();
  }

  public void visit(CardVisitor v) {
    for( Card c : cards ) {
      v.visit(c);
    }
  }

  public void shuffle() {
    for( int i=0 ; i < 6 ; i++ ) {
      shuffleBase();
    }
  }

  private void shuffleBase() {
    List<Card> top = cards.subList(0, (cards.size()) / 2);
    List<Card> bot = cards.subList((cards.size()) / 2, cards.size());

    List<Card> res = new ArrayList<>(cards.size());

    Random rand = new Random(42);

    int ti = 0;
    int bi = 0;
    while( ti < top.size() && bi < bot.size() ) {
      double topBefore = rand.nextDouble();
      double tRand = rand.nextDouble();
      double bRand = rand.nextDouble();

      if( topBefore < 0.5 ) {
        res.add(top.get(ti++));
        if (tRand > 0.7 && ti < top.size()) {
          res.add(top.get(ti++));
        }
        res.add(bot.get(bi++));
        if (bRand > 0.7 && bi < bot.size()) {
          res.add(bot.get(bi++));
        }
      } else {
        res.add(bot.get(bi++));
        if (bRand > 0.7 && bi < bot.size()) {
          res.add(bot.get(bi++));
        }
        res.add(top.get(ti++));
        if (tRand > 0.7 && ti < top.size()) {
          res.add(top.get(ti++));
        }
      }
    }

    for(  ; ti < top.size() ; ti++ ) {
      res.add(top.get(ti));
    }
    for(  ; bi < bot.size() ; bi++ ) {
      res.add(bot.get(bi));
    }

    cards = res;
  }
}
