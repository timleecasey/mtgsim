package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import org.tlc.mtg.nouns.CardSorts;
import org.tlc.mtg.nouns.Mana;
import org.tlc.mtg.nouns.Phases;
import org.tlc.mtg.nouns.ResolvedType;
import org.tlc.mtg.nouns.Stage;
import org.tlc.mtg.sim.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 */
public class Player {
  private int turn;
  private int life;
  private Cards deck = new Cards();
  private Hand hand = new Hand();
  private Board board = new Board();
  private Cards out = new Cards();
  private List<Stage<Player>> stages = new ArrayList<>();
  private Stats[] stats = null;
  private Stats cur;

  private boolean constrained;

  public Player(Stats[] stats) {
    this.stats = stats;
    resetPlayer(0);
  }

  public void resetPlayer(int toDepth) {
    constrained = false;
    turn = 0;
    life = 20;
    deck.reset();
    hand.reset();
    board.reset();
    out.reset();
  }

  /**
   * Given a hand and a board with lands, find the maximal thing to cast in hand.
   * This returns a card, but makes no changes to the hand or board.  This is a simple match of resources to cards.
   * @return the card to cast.
   */
  public Card findTopCast() {
    Card ret = null;
    Cards.CardCollector cc = new Cards.CardCollector();
    getHand().visit(cc);
    Collections.sort(cc.getCollected(), new CardSorts.HiToLowDamageSort());
    for( Card c : cc.getCollected() ) {
      if( canCast(c) ) {
        ret = c;
        break;
      }
    }
    return ret;
  }

  public void placeLand(Card c) {
    c.applyPhase(Phases.PUT_INTO_PLAY);
    getBoard().getLand().add(c);
  }

  /**
   * Given the card in your hand, place this card as a spell.
   * @param c a given card in your hand
   */
  public void castSpell(Card c) {
    Set<Card> plan = castPlan(c);
    if( plan == null )
      return;

    if( ! hand.takeSpecific(c) ) {
      return;
    }

    c.applyPhase(Phases.CAST);

    c.resType.play(getBoard(), c);

    for( Card land : plan ) {
      land.tapped = true;
    }
  }

  public void initialDraw() {
    List<Card> draw = deck.takeFromTop(7);
    for( Card c : draw ) {
      c.applyPhase(Phases.DRAW);
    }
    hand.resetAndAddAllCards(draw);
  }

  public void addToLife(int n) {
    life += n;
  }

  public void removeFromLife(int n) {
    life -= n;
  }

  protected boolean canCast(Card c) {
    Set<Card> plan = castPlan(c);
    if( plan == null )
      return false;
    if( c.cost.hasCost && plan.size() == c.cost.slots.length ) {
      return true;
    } else {
      return false;
    }
  }

  protected Set<Card> castPlan(Card c) {
    if( c.cost.hasCost ) {
      Set<Card> toBeUsed = new HashSet<>();
      Cards.CardCollector cc = new Cards.CardCollector();
      getBoard().getLand().visit(cc);
      List<Card> src = cc.getCollected();
      for( Mana m : c.cost.slots) {
        Card land = findAndRemove(src, m);
        if( land == null ) {
          return null;
        }
        toBeUsed.add(land);
      }
      return toBeUsed;
    } else {
      return new HashSet<>();
    }
  }

  protected Card findAndRemove(List<Card> src, Mana m) {
    Card ret = null;
    for( Card c : src ) {
      if( ! c.resType.equals(ResolvedType.LAND) ) {
        continue;
      }
      if( c.tapped ) {
        continue;
      }
      if( m.accepts(c) ) {
        ret = c;
        break;
      }
    }

    if( ret != null ) {
      src.remove(ret);
    }
    return ret;
  }

  public int getLife() {
    return life;
  }

  public Cards getDeck() {
    return deck;
  }

  public Hand getHand() {
    return hand;
  }

  public Board getBoard() {
    return board;
  }

  public Cards getOut() {
    return out;
  }

  public void incTurnAndCur() {
    cur = stats[turn];
    turn++;
  }

  public int getTurn() {
    return turn;
  }

  public boolean isConstrained() {
    return constrained;
  }

  public void setConstrained(boolean constrained) {
    this.constrained = constrained;
  }

  public List<Stage<Player>> getStages() {
    return stages;
  }

  public Stats[] getStats() {
    return stats;
  }

  public Stats getCur() {
    return cur;
  }

  @Override
  public String toString() {
    return "L: " + life + " H: " + hand.depth() + " D: " + deck.depth() + " @ T " + turn;
  }


}
