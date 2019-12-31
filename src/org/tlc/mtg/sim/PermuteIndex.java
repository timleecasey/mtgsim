package org.tlc.mtg.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 */
public class PermuteIndex {
    public interface PermuteVisitor {
      void visit(int[] a);
    }

    public interface PermuteListVisitor {
      void visit(List<Integer> a);
    }

    private int[] a;

    public PermuteIndex(int[] a) {
        this.a = a;
    }

    public void trials(PermuteListVisitor v, long count) {
      Random r = new Random(42);
      ArrayList<Integer> ar = new ArrayList<>(a.length);
      for( int i : a ) {
        ar.add(i);
      }
      for( int i=0 ; i < count ; i++ ) {
        Collections.shuffle(ar, r);
        v.visit(ar);
      }
    }

    public void generate(PermuteVisitor v) {
        generate(0, a.length, v);
    }

    public void generate(int start, int len, PermuteVisitor v) {
        int c;
        if (len - start <= 1) {
            v.visit(a);
            return;
        }
        for( c=start ; c < len ; c++) {
            swap(c, len - 1);
            generate(start, len-1, v);
            swap(c, len - 1);
        }
    }

    protected void swap(int from, int to) {
        int tmp = a[from];
        a[from] = a[to];
        a[to] = tmp;
    }

}