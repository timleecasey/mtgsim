package org.tlc.mtg.nouns;

/**
 * Parts of key words have points at which they activate.
 * These are the stages of activation for all key words.
 *
 * For example "Vigilance" does not tap during attack.
 * This means Vigilance is implemented with a composed attack, but no tap.
 * Double Strike is an attack which repeats the damage, some as composed with first strike and normal damage.
 */
public enum StageNode {
  /**
   * Card load
   */
  LOAD,

  UNTAP,
  UPKEEP,
  DRAW,
  LAND,
  SPELLS,
  ATTACK,
  CLEANUP,
  END,

  /**
   * Tapping
   */
  TAP,
  /**
   * Declaration of attack
   */
  ATTACK_DECL,
  /**
   * Addition to an attack.
   */
  ATTACK_ADD,
  /**
   * Defence declaration.
   */
  DEFEND_DECL,
  /**
   * Add to defenders.
   */
  DEFEND_ADD,
  /**
   * When a card is cast
   */
  CAST,
  /**
   * After spell resolution, damage
   */
  DAMAGE_RESOLUTION,
  /**
   * The actual damage.
   */
  DAMAGE,


}
