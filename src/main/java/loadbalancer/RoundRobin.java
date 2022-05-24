package loadbalancer;

import com.google.common.collect.ImmutableList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin implements LoadBalancer {

    private final List<String> availableHosts = ImmutableList.copyOf(new ArrayList<>(Hosts.ROUND_ROBIN.keySet()));
    private AtomicInteger pointer = new AtomicInteger(0);

    @Override
    public String getTargetHost(URL url) {
        if (pointer.get() >= availableHosts.size()) {
            pointer = new AtomicInteger(0);
        }
        return availableHosts.get(pointer.getAndIncrement());
    }
}
