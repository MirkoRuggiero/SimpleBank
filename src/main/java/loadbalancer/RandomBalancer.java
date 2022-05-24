package loadbalancer;

import java.net.URL;

public class RandomBalancer implements LoadBalancer{
    @Override
    public String getTargetHost(URL url) {
        return null;
    }
}
