package loadbalancer;

import java.net.URL;

public interface LoadBalancer {
    String getTargetHost(URL url);
}
