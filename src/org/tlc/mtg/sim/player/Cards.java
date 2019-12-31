package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.ResolvedType;
import org.tlc.mtg.sim.Stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 */
public class Cards {
  //
  // Top is at 0, bottom is at cards.size()-1.
  //
  protected List<Card> cards = new ArrayList<>();

  public interface CardVisitor {
    /**
     * Handed a single card for each card in the given pile
     * @param c each card in the pile, in order.
     */
    public void visit(Card c);
  }

  public static class CardCollector implements CardVisitor {
    protected List<Card> collected = new ArrayList<>();
    @Override
    public void visit(Card c) {
      collected.add(c);
    }

    public List<Card> getCollected() {
      return collected;
    }
  }

  public static class ManaSourceCollector implements CardVisitor {
    protected List<Card> collected = new ArrayList<>();
    @Override
    public void visit(Card c) {
      boolean add = false;
      if( c.resType.equals(ResolvedType.LAND) ) {
        add = true;
      }

      if( !add && c.manaSrc != null ) {
        add = true;
      }

      if( add && ! c.tapped ) {
        collected.add(c);
      }
    }

    public List<Card> getCollected() {
      return collected;
    }
  }

  public void add(Card c) {
    cards.add(c);
  }

  /**
   * How many cards are in this pile.
   * @return the depth of the pile.
   */
  public int depth() {
    return cards.size();
  }

  /**
   * Reset the card state to empty.
   */
  public void reset() {
    cards.clear();
  }

  /**
   * Reset, clearing the current card pile, add in the rest of these cards.
   * @param src the source to add in after resetting
   */
  public void resetAndAddAllCards(Collection<Card> src) {
    reset();
    cards.addAll(src);
  }

  /**
   * If there are more than 0 cards, return true.
   * @return true if there is at least one card in this pile
   */
  public boolean hasSome() {
    return depth() > 0;
  }

  public Card hasOneOrNull(final ResolvedType type) {
    final List<Card> ret = new ArrayList<>();
    visit(new CardVisitor() {
      @Override
      public void visit(Card c) {
        if( c.resType.equals(type) ) {
          ret.add(c);
        }
      }
    });

    if( ret.size() > 0 ) {
      return ret.get(0);
    }
    return null;
  }

  /**
   * Remove the top card from these cards, or null.
   * @return the top card, or null if empty
   */
  public Card pullTopOne() {
    if( cards.size() == 0 ) {
      return null;
    }
    return cards.remove(0);
  }

  /**
   * Take a specified number of cards from the top.
   * @param n the number of cards to take.  This may take fewer cards then specified if fewer cards are required.
   * @return the list of cards taken.
   */
  public List<Card> takeFromTop(int n) {
    List<Card> ret = new ArrayList<>();
    for( int i=0 ; i < n ; i++ ) {
      Card c = pullTopOne();
      if( c != null ) {
        ret.add(c);
      }
    }
    return ret;
  }

  /**
   * Take a specific card from the given set of cards
   * @param c the specific card object to take and remove
   * @return returns the card argument.
   */
  public boolean takeSpecific(Card c) {
    return cards.remove(c);
  }

  /**
   * Visit all cards with the given card visitor.
   * @param v the visitor
   */
  public void visit(CardVisitor v) {
    for( Card c : cards ) {
      v.visit(c);
    }
  }

  /**
   * Shuffle the given cards.  This routine simulates a bridge shuffle.
   */
  public void shuffle() {
    //
    // http://www.kibble.net/magic/magic09.php
    //
    for( int i=0 ; i < 8 ; i++ ) {
      shuffleRound();
    }
  }

  private void shuffleRound() {
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
