package org.tlc.mtg.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class DateTimeUtils {
    public static final String LOG_TM_FMT = "YYYYMMdd_HHmmss";
    protected static final String ISO_8601_TM_FMT = "YYYYMMdd'T'HHmmss";
    protected static final SimpleDateFormat dateTimeFmt = new SimpleDateFormat(LOG_TM_FMT);
    protected static final SimpleDateFormat dateFmt = new SimpleDateFormat("YYYYMMdd");


    public static long SEC_MS = 1000;
    public static long MIN_MS = 60 * SEC_MS;
    public static long HR_MS = 60 * MIN_MS;
    public static long DAY_MS = 24 * HR_MS;

    public static class TimeScale {
        long ms;
        String unit;
        int ordinal;

        public TimeScale(long ms, String unit, int ordinal) {
            this.ms = ms;
            this.unit = unit;
            this.ordinal = ordinal;
        }

        public long getMs() {
            return ms;
        }

        public String getUnit() {
            return unit;
        }

        public int getOrdinal() {
            return ordinal;
        }
    }
    protected static TimeScale[] scales = {
        new TimeScale(
            1,
            "ms",
            0
        ),
        new TimeScale(
            SEC_MS,
            "s",
            1
        ),
        new TimeScale(
            MIN_MS,
            "m",
            2
        ),
        new TimeScale(
            HR_MS,
            "h",
            3
        ),
        new TimeScale(
            DAY_MS,
            "d",
            4
        ),
    };

    public static String formatTime(long diff) {
        long[] unitTimes = new long[scales.length];
        for( TimeScale ts : scales ) {
            unitTimes[ts.ordinal] = diff / ts.ms;
        }

        for( int i=scales.length - 1 ; i >=0 ; i-- ) {
            if( unitTimes[i] > 0 ) {
                if( i > 0 ) {
                    unitTimes[i-1] = (diff - unitTimes[i] * scales[i].ms) / scales[i-1].ms;
                }
                return formatTime(unitTimes, i);
            }
        }

        return "0s 0ms";
    }

    protected static String formatTime(long[] tms, int i) {
        if( i == 0 ) {
            return tms[0] + "ms";
        }

        return tms[i] + scales[i].unit + " " + tms[i-1] + scales[i-1].unit;
    }

    public static String simpleDateTimeFmt(long tm) {
        return simpleDateTimeFmt(new Date(tm));
    }

    public static String simpleDateTimeFmt(Date d) {
        return dateTimeFmt.format(d);
    }
    public static String simpleDateFmt(long tm) {
        return simpleDateFmt(new Date(tm));
    }

    public static String simpleDateFmt(Date d) {
        return dateFmt.format(d);
    }
}
