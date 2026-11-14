package spring.examples.tutorial.counter.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class CounterService {
    private final AtomicInteger hits = new AtomicInteger(1);

    public int getHits() {
        return hits.getAndIncrement();
    }
}
