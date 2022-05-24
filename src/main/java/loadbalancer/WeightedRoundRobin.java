package loadbalancer;

import com.google.common.collect.ImmutableList;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightedRoundRobin implements LoadBalancer {

    private final List<String> availableHosts = weightedHostList(Hosts.WEIGHTED_ROUND_ROBIN);
    private AtomicInteger pointer = new AtomicInteger(0);

    private static ImmutableList<String> weightedHostList(Map<String, Integer> availableHosts) {
        List<String> b = new ArrayList<>();
        for (Map.Entry<String, Integer> e : availableHosts.entrySet()) {
            int times = e.getValue();
            for (int i = 0; i < times; i++) {
                b.add(e.getKey());
            }
        }
        Collections.shuffle(b);
        return ImmutableList.copyOf(b);
    }

    @Override
    public String getTargetHost(URL url) {
        if (pointer.get() >= availableHosts.size()) {
            pointer = new AtomicInteger(0);
        }
        return availableHosts.get(pointer.getAndIncrement());
    }
}
