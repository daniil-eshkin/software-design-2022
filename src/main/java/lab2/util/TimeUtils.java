package lab2.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeUtils {
    public static int toUnixTimeStamp(Date date) {
        return (int)(date.getTime() / 1000L);
    }

    public static Date hoursBefore(Date date, int hours) {
        assert hours >= 0;

        return new Date(date.getTime() - Duration.ofHours(hours).toMillis());
    }

    public static List<Integer> getHistTimestampsUntilDate(Date date, int hours) {
        assert hours > 0;

        List<Integer> res = new ArrayList<>();
        for (int i = hours; i >= 0; i--) {
            res.add(toUnixTimeStamp(hoursBefore(date, i)));
        }

        return res;
    }
}
