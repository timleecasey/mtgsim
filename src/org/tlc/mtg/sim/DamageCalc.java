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

    public long damage() {

        long sum = 0;
        int step = 0;
        int len = src.size();

        for( Card c : src ) {
            sum += c.computeDamage(step, len);
            step++;
        }

        return sum;
    }
}
