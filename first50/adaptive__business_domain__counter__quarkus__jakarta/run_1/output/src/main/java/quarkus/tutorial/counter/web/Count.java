package quarkus.tutorial.counter.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import quarkus.tutorial.counter.ejb.CounterBean;

@Named
@RequestScoped
public class Count {
    @Inject
    private CounterBean counterBean;

    private Integer hitCount;

    public int getHitCount() {
        if (hitCount == null) {
            hitCount = counterBean.getHits();
        }
        return hitCount;
    }

    public void setHitCount(int newHits) {
        this.hitCount = newHits;
    }
}
