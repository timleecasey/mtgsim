package org.tlc.mtg.util;

/**
 */
public class DoubleCounter {
    private double value;

    public double getValue() {
        return value;
    }

    public void inc() {
        value++;
    }

    public void inc(double d) {
        value += d;
    }

    public void reset() {
        value = 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
