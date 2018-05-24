package org.tlc.mtg.sim.player;

/**
 */
public class Board {
  private ExposedCards land;
  private BoardCritters critters;
  private ExposedCards enchantments;
  private ExposedCards artifacts;
  private ExposedCards planesWalkers;

  public Board() {
    land = new ExposedCards();
    critters = new BoardCritters();
    enchantments = new ExposedCards();
    artifacts = new ExposedCards();
    planesWalkers = new ExposedCards();
  }

  public void reset() {
    land.reset();
    critters.reset();
    enchantments.reset();
    artifacts.reset();
    planesWalkers.reset();
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
}
