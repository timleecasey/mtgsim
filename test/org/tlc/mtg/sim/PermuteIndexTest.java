package org.tlc.mtg.sim;

import org.junit.Assert;
import org.junit.Test;
import org.tlc.mtg.util.Counter;

import java.util.HashSet;
import java.util.Set;

public class PermuteIndexTest {

    @Test
    public void mustPermute() throws Exception {
        int a[] = makeArr(5);
        PermuteIndex pi = new PermuteIndex(a);
        CountingVisitor v = new CountingVisitor();
        pi.generate(v);
        Assert.assertEquals(120, v.c.getValue());
        Assert.assertEquals(120, v.seen.size());
    }

    protected static int[] makeArr(int n) {
        int ret[] = new int[n];
        for( int i=0 ; i < n ; i++ ) {
            ret[i] = i;
        }
        return ret;
    }

    public static class CountingVisitor implements PermuteIndex.PermuteVisitor {
        Counter c = new Counter();
        Set<String> seen = new HashSet<>();

        @Override
        public void visit(int[] a) {
            c.inc();
            StringBuilder sb = new StringBuilder();
            for( int i=0 ; i < a.length ; i++ ) {
                if( sb.length() > 0 ) {
                    sb.append(".");
                }
                sb.append(a[i]);
            }
            seen.add(sb.toString());
        }
    }
}