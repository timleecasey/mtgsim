package org.tlc.mtg.util;

/**
 */
public class ArrayUtils {
    public static final int[] EMPTY_INT = new int[0];
    public static final double[] EMPTY_DOUBLE = new double[0];
    public static final long[] EMPTY_LONG = new long[0];
    public static final String[] EMPTY_STRING = new String[0];

    public static int[] appendIntArray(int[] v, int value) {
        int[] tmp = new int[v.length + 1];
        System.arraycopy(v, 0, tmp, 0, v.length);
        tmp[v.length] = value;
        return tmp;
    }

    public static double[] appendDoubleArray(double[] v, double value) {
        double[] tmp = new double[v.length + 1];
        System.arraycopy(v, 0, tmp, 0, v.length);
        tmp[v.length] = value;
        return tmp;
    }

    public static long[] appendLongArray(long[] v, long value) {
        long[] tmp = new long[v.length + 1];
        System.arraycopy(v, 0, tmp, 0, v.length);
        tmp[v.length] = value;
        return tmp;
    }
}
