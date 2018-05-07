package org.tlc.mtg.util;

import java.util.*;

/**
 */
public class Metrics {

    private Map<String, Counter> map = new HashMap<String, Counter>();
    public void inc(String s) {
        Counter c = map.get(s);
        if( c == null ) {
            c = new Counter();
            map.put(s, c);
        }

        c.inc();
    }

    public void inc(String s, int cnt) {
        Counter c = map.get(s);
        if( c == null ) {
            c = new Counter();
            map.put(s, c);
        }

        c.inc(cnt);
    }

    public String dump(String pre) {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        for( String k : keys ) {
            Counter c = map.get(k);
            sb.append("\n").append(pre).append(k).append(": ").append(c);
//            System.out.print(pre);
//            System.out.println(k + ": " + c);
        }
        return sb.toString();
    }
}
