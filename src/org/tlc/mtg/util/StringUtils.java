package org.tlc.mtg.util;

import java.util.List;

/**
 */
public class StringUtils {

    public static String construct(int... ia) {
        StringBuilder sb = new StringBuilder("[");
        for( int i : ia ) {
            if( sb.length() > 1 )
                sb.append(",");
            sb.append(i);
        }
        sb.append("]");
        return sb.toString();

    }

    public static String join(String sep, String... sa) {
        StringBuilder sb = new StringBuilder();
        for( String s : sa ) {
            if( sb.length() > 0 )
                sb.append(sep);
            sb.append(s);
        }
        return sb.toString();

    }

    public static String construct(String... sa) {
        StringBuilder sb = new StringBuilder("[");
        for( String s : sa ) {
            if( sb.length() > 0 )
                sb.append(",");
            sb.append(s);
        }
        sb.append("]");
        return sb.toString();

    }

    public static String construct(List<String> sl) {
        StringBuilder sb = new StringBuilder("[");
        for( String s : sl ) {
            if( sb.length() > 0 )
                sb.append(",");
            sb.append(s);
        }
        sb.append("]");
        return sb.toString();
    }

    public static boolean isNumber(String s) {
        for( char c : s.toCharArray() ) {
            if( c == '.' )
                continue;
            if( c == '%' )
                continue;

            if( c > '9' || c < '0' )
                return false;

        }
        return true;
    }

    private static char[] hex = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
    };

    public static String toHex(byte[] ba) {
        StringBuilder sb = new StringBuilder();
        for( int b : ba ) {

            b = b & 0xff;
            int low = b % 16;
            int high = b / 16;

            sb.append(hex[high]);
            sb.append(hex[low]);
        }

        return sb.toString();
    }

}
