package loadbalancer;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Hosts {
    static final Map<String, Integer> ROUND_ROBIN = ImmutableMap.of(
            "110.10.10.10",1,
            "110.11.11.11",1,
            "110.12.12.12",1);

    static final Map<String, Integer> WEIGHTED_ROUND_ROBIN = ImmutableMap.of(
            "110.10.10.10",1,
            "110.11.11.11",2,
            "110.12.12.12",3);
}
