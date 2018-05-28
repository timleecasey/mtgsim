package org.tlc.mtg.sim.player;

import org.tlc.mtg.nouns.Card;
import sun.jvm.hotspot.debugger.win32.coff.ExportDirectoryTable;

/**
 */
public class Board {
  private ExposedCards land;
  private BoardCritters critters;
  private ExposedCards enchantments;
  private ExposedCards artifacts;
  private ExposedCards planesWalkers;
  private ExposedCards grave;
  private ExposedCards attackSet;

  public Board() {
    land = new ExposedCards();
    critters = new BoardCritters();
    enchantments = new ExposedCards();
    artifacts = new ExposedCards();
    planesWalkers = new ExposedCards();
    grave = new ExposedCards();
    attackSet = new ExposedCards();
  }

  public void reset() {
    land.reset();
    critters.reset();
    enchantments.reset();
    artifacts.reset();
    planesWalkers.reset();
    grave.reset();
    attackSet.reset();
  }

  public void untap() {
    Cards.CardVisitor v = new UntapVisitor();
    land.visit(v);
    artifacts.visit(v);
    planesWalkers.visit(v);
    critters.visit(v);
  }

  public ExposedCards getLand() {
    return land;
  }

  public BoardCritters getCritters() {
    return critters;
  }

  public ExposedCards getEnchantments() {
    return enchantments;
  }

  public ExposedCards getArtifacts() {
    return artifacts;
  }

  public ExposedCards getPlanesWalkers() {
    return planesWalkers;
  }

  public ExposedCards getGrave() {
    return grave;
  }

  public ExposedCards getAttackSet() {
    return attackSet;
  }

  public static class UntapVisitor implements Cards.CardVisitor{

    @Override
    public void visit(Card c) {
      c.tapped = false;
    }
  }
}
