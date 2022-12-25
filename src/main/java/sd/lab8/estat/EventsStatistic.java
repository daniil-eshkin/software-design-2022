package sd.lab8.estat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public interface EventsStatistic {
    void incEvent(String name);
    Statistic getEventStatisticByName(String name);
    List<Statistic> getAllEventStatistic();
    void printStatistic();

    @Data
    @AllArgsConstructor
    class Statistic {
        private String name;
        private double rpm;

        @Override
        public String toString() {
            return String.format("%s: %f", name, rpm);
        }
    }
}
