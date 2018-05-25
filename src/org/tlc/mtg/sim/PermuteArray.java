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
        for( int i=0 ; i < a.length ; i++ ) {
            a[i] = i;
        }
        this.src = src;
        this.vSrc = new ArrayList<T>(src.size());
        indexes = new PermuteIndex(a);
    }

    public void permutations(final PermuteListVisitor<T> v) {
        indexes.generate(new PermuteIndex.PermuteVisitor() {
            @Override
            public void visit(int[] a) {
                vSrc.clear();
                for( int i=0 ; i < src.size() ; i++ ) {
                    vSrc.add(src.get(a[i]));
                }
                v.visit(vSrc);
            }
        });
    }

    public void trials(PermuteListVisitor<T> v) {
        indexes.trials(new PermuteIndex.PermuteListVisitor() {
            @Override
            public void visit(List<Integer> a) {
                vSrc.clear();
                for( int i=0 ; i < src.size() ; i++ ) {
                    vSrc.add(src.get(a.get(i)));
                }
                v.visit(vSrc);
            }
        }, 1000000);
    }
}
