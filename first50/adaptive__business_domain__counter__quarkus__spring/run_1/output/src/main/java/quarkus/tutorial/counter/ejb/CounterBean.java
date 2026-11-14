package quarkus.tutorial.counter.ejb;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class CounterBean {
    private final AtomicInteger hits = new AtomicInteger(1);

    // Increment and return the number of hits
    public int getHits() {
        return hits.getAndIncrement();
    }
}
