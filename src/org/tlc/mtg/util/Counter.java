package org.tlc.mtg.util;

/**
 */
public class Counter {
    private long value;

    public long getValue() {
        return value;
    }

    public void inc() {
        value++;
    }

    public void inc(int n) {
        value += n;
    }

    public void inc(long l) {
        value += l;
    }

    public void reset() {
        value = 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
