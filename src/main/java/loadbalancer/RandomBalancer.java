package loadbalancer;

import java.net.URL;
import java.util.List;
import java.util.Random;

public class RandomBalancer implements LoadBalancer {
    private final List<String> availableHosts = Hosts.RANDOM;
    private final Random random = new Random();

    @Override
    public String getTargetHost(URL url) {
        int randomIndex = random.nextInt(availableHosts.size());
        return availableHosts.get(randomIndex);
    }
}

