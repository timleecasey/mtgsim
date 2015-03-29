package org.tlc.mtg.sim;

import org.tlc.mtg.nouns.Card;

import java.util.List;

/**
 */
public class DamageCalc {
    private List<Card> src;

    public DamageCalc(List<Card> src) {
        this.src = src;
    }

    public int damage() {

        int sum = 0;

        for( Card c : src ) {

        }

        return sum;
    }
}
