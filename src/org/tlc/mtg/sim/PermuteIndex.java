package org.tlc.mtg.sim;

/**
 */
public class PermuteIndex {
    public interface PermuteVisitor {
        public void visit(int[] a);
    }

    private int[] a;

    public PermuteIndex(int[] a) {
        this.a = a;
    }

    public void generate(PermuteVisitor v) {
        generate(0, a.length, v);
    }

    public void generate(int start, int len, PermuteVisitor v) {
        int c;
        if (len - start == 1) {
            v.visit(a);
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