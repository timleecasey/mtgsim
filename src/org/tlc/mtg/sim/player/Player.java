package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;

import java.util.List;

/**
 */
public class Player {
  private int turn;
  private int life;
  private Cards deck = new Cards();
  private Hand hand = new Hand();
  private Board board = new Board();
  private Cards grave = new Cards();
  private Cards out = new Cards();

  private boolean constrained;

  public Player() {
    resetPlayer();
  }

  public void resetPlayer() {
    constrained = false;
    turn = 0;
    life = 20;
    deck.reset();
    hand.reset();
    board.reset();
    grave.reset();
    out.reset();
  }

  public void initialDraw() {
    List<Card> draw = deck.takeFromTop(7);
    hand.resetAndAddAllCards(draw);
  }

  public void addToLife(int n) {
    life += n;
  }

  public void removeFromLife(int n) {
    life -= n;
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

  public Cards getGrave() {
    return grave;
  }

  public Cards getOut() {
    return out;
  }

  public void incTurn() {
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
}
