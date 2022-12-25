package sd.lab8.estat;

import sd.lab8.clock.SettableClock;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class EventsStatisticTest {
    private static final Instant INITIAL = Instant.parse("2022-12-20T21:00:00.00Z");
    private final SettableClock clock = new SettableClock(INITIAL);
    private EventsStatistic eventsStatistic;

    @Before
    public void before() {
        clock.setNow(INITIAL);
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    public void emptyTest() {
        assertThat(eventsStatistic.getAllEventStatistic()).isEmpty();
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(0);
    }

    @Test
    public void singleEventTest() {
        // Empty
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(0);

        eventsStatistic.incEvent("test");
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(1.0 / 60);
        clock.setNow(INITIAL.plus(Duration.ofMinutes(10)));
        eventsStatistic.incEvent("test");
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(2.0 / 60);
        clock.setNow(INITIAL.plus(Duration.ofHours(1)));
        eventsStatistic.incEvent("test");
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(3.0 / 60);

        // First event is older than one hour
        clock.setNow(INITIAL.plus(Duration.ofMinutes(70)));
        eventsStatistic.incEvent("test");
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(3.0 / 60);

        // All events are outdated
        clock.setNow(INITIAL.plus(Duration.ofHours(3)));
        assertThat(eventsStatistic.getEventStatisticByName("test").getRpm()).isEqualTo(0);
    }

    @Test
    public void multipleEventsTest() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < i; j++) {
                clock.setNow(INITIAL.plus(Duration.ofMinutes(10 * j)));
                eventsStatistic.incEvent("test" + i);
            }
            assertThat(eventsStatistic.getEventStatisticByName("test" + i).getRpm()).isEqualTo((double)i / 60);
        }
        assertThat(eventsStatistic.getAllEventStatistic()).containsExactlyInAnyOrderElementsOf(
                IntStream.rangeClosed(1, 5)
                        .mapToObj(i -> new EventsStatistic.Statistic("test" + i, (double)i / 60))
                        .collect(Collectors.toList())
        );

        eventsStatistic.printStatistic();
    }

    @Test
    public void bigTest() {
        Random rand = new Random();
        Set<Integer> events = new TreeSet<>();
        for (int i = 0; i < 6000; i++) {
            clock.setNow(INITIAL.plus(Duration.ofMinutes(i)));
            int event = rand.nextInt();
            events.add(event);
            eventsStatistic.incEvent("test" + event);

            Set<EventsStatistic.Statistic> statistics = events.stream()
                    .map(e -> eventsStatistic.getEventStatisticByName("test" + e))
                    .filter(s -> s.getRpm() != 0)
                    .collect(Collectors.toSet());

            assertThat(eventsStatistic.getAllEventStatistic()).containsExactlyInAnyOrderElementsOf(statistics);
        }
    }
}
