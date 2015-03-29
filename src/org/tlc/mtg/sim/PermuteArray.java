package org.tlc.mtg.sim;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class PermuteArray<T> {

    public interface PermuteListVisitor<T> {
        public void visit(List<T> cur);
    }

    private PermuteIndex indexes;
    private List<T> src;
    private List<T> vSrc;

    public PermuteArray(List<T> src) {
        int[] a = new int[src.size()];
        this.src = src;
        this.vSrc = new ArrayList<T>(src.size());
        indexes = new PermuteIndex(a);
    }

    public void visit(final PermuteListVisitor<T> v) {
        indexes.generate(new PermuteIndex.PermuteVisitor() {
            @Override
            public void visit(int[] a) {
                for( int i=0 ; i < src.size() ; i++ ) {
                    vSrc.add(src.get(a[i]));
                    v.visit(vSrc);
                }
            }
        });
    }
}
