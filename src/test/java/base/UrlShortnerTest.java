package base;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Random;

import static base.UrlShortner.convert;
import static base.UrlShortner.map;

public class UrlShortnerTest {

    @Test
    public void test() {
        String input = "http://looooong.com/somepath";
        String keyword = "MY-NEW-WS";
        String expected = "http://short.com/MY-NEW-WS";

        MatcherAssert.assertThat(convert(input, keyword), CoreMatchers.is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_keyword() {
        String input = "http://looooong.com/somepath";
        String keyword = "MY-NEW-WSSSSSSSSSSSSSSSSSSSSSSSSSSS";
        convert(input, keyword);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_URL() {
        String inputURL = "invalid";
        String keyword = "MY-NEW-WS";
        convert(inputURL, keyword);
    }

    @Test
    public void test_new_conversion_logic() {
        String inputUrl = "http://looooong.com/somepath";
        long now = System.currentTimeMillis();
        System.out.println("seed " + now);
        Random random = new Random(1652353019089L);
        String shortUrl = convert(inputUrl, random);
        String randomString = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        System.out.println(randomString);
        MatcherAssert.assertThat(randomString.length(), CoreMatchers.is(4));
        randomString.chars().forEach(c -> {
            int v = (int) c;
            MatcherAssert.assertThat(map.values().stream().anyMatch(p -> v >= p.getKey() && v <= p.getValue()),
                    CoreMatchers.is(true));

        });
    }
}