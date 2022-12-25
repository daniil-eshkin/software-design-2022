package sd.lab8.estat;

import sd.lab8.clock.Clock;
import sd.lab8.clock.NormalClock;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    private final Duration DROP_INTERVAL = Duration.ofHours(1);
    private final Clock clock;
    private final Map<String, List<Instant>> events = new HashMap<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    public EventsStatisticImpl() {
        this(new NormalClock());
    }

    @Override
    public void incEvent(String name) {
        getEvents(name).add(clock.now());
    }

    @Override
    public Statistic getEventStatisticByName(String name) {
        dropInstants(name);
        return Optional.ofNullable(events.get(name))
                .map(instants -> new Statistic(name, countRpm(instants)))
                .orElse(new Statistic(name, 0));
    }

    @Override
    public List<Statistic> getAllEventStatistic() {
        return events.entrySet().stream()
                .map(e -> {
                    dropInstants(e.getKey());
                    return new Statistic(e.getKey(), countRpm(e.getValue()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public void printStatistic() {
        getAllEventStatistic()
                .forEach(System.out::println);
    }

    private List<Instant> getEvents(String name) {
        return Optional.ofNullable(events.get(name))
                .orElseGet(() -> {
                    List<Instant> instants = new LinkedList<>();
                    events.put(name, instants);
                    return instants;
                });
    }

    private double countRpm(List<Instant> instants) {
        return (double)instants.size() / DROP_INTERVAL.toMinutes();
    }

    private void dropInstants(String name) {
        var now = clock.now();
        Optional.ofNullable(events.get(name))
                .ifPresent(instants -> {
                    var newInstants = instants.stream()
                            .dropWhile(i -> Duration.between(i, now).compareTo(DROP_INTERVAL) > 0)
                            .collect(Collectors.toList());
                    if (newInstants.isEmpty()) {
                        events.remove(name);
                    } else {
                        events.put(name, newInstants);
                    }
                });
    }
}
