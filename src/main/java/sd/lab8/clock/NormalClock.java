package sd.lab8.clock;

import java.time.Instant;

public class NormalClock implements Clock {
    @Override
    public Instant now() {
        return Instant.now();
    }
}
