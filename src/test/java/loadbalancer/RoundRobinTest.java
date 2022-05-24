package loadbalancer;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class RoundRobinTest {

    @Test
    public void test_roundrobin() {
        LoadBalancer balancer = new RoundRobin();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            res.add(balancer.getTargetHost(null));
        }
        assertThat(res.size(), is(9));
        Map<String, Integer> map = new HashMap<>();
        for (String t : res) {
            if (map.computeIfPresent(t, (k, i) -> 1 + map.get(k)) == null) {
                map.put(t, 1);
            }
        }
        assertThat(map.values().stream().allMatch(i -> i == 3), is(true));
    }

    @Test
    public void test_roundrobin_weighted() {
        LoadBalancer balancer = new WeightedRoundRobin();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            res.add(balancer.getTargetHost(null));
        }
        assertThat(res.size(), is(9));
        Map<String, Integer> map = new HashMap<>();
        for (String t : res) {
            if (map.computeIfPresent(t, (k, i) -> 1 + map.get(k)) == null) {
                map.put(t, 1);
            }
        }
        for (Map.Entry<String, Integer> e : map.entrySet()) {

        }
    }
}