package lab2.util;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeUtilsTest {
    private static final Date date = new Date(100000000L);

    @Test
    public void getHistTimestampsUntilDate() {
        List<Integer> stamps = TimeUtils.getHistTimestampsUntilDate(date, 1);

        assertThat(stamps).isEqualTo(List.of(96400, 100000));
    }
}
