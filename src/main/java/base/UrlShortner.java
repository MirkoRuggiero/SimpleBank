package base;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class UrlShortner {
    //    Given as input a URL and a SEO keyword with a max length of 20 characters, chosen by the user, generate a SEO URL.
//
//            Example:
//
//    Input:
//    URL: http://looooong.com/somepath
//    SEO keyword: MY-NEW-WS
//    Output:
//    URL: http://short.com/MY-NEW-WS


//    Given a URL as input, generate a short URL with a path made of a random sequence of 4 alphanumeric characters.
//
//    Example:
//
//    Input:
//    URL: http://looooong.com/somepath
//    Output:
//    URL: http://short.com/ZfGd


    private static final String baseDomain = "http://short.com";

    public static String convert(String inputURL, String keyword) {
        if (!validate(inputURL)) {
            throw new IllegalArgumentException("URL invalid");
        }
        if (Objects.requireNonNull(keyword, "SEO keyword cannot be null").length() > 20) {
            throw new IllegalArgumentException("keyword length not valid");
        }
        return String.format("%s/%s", baseDomain, keyword);
    }

    @VisibleForTesting
    static final Map<Integer, Map.Entry<Integer, Integer>> map = ImmutableMap.of(
            0, new AbstractMap.SimpleEntry<>(48, 57),
            1, new AbstractMap.SimpleEntry<>(65, 90),
            2, new AbstractMap.SimpleEntry<>(97, 122)
    );

    public static String convert(String inputUrl, Random random) {
        StringBuilder sb = new StringBuilder();
        //digits 48-57
        //upper case 65-90
        //lower 97 122
        for (int i = 0; i < 4; i++) {
            int typeOfChar = random.nextInt(Integer.MAX_VALUE) % 2;
            Map.Entry<Integer, Integer> range = map.get(typeOfChar);
            Integer lowerBound = range.getKey();
            Integer upperBound = range.getValue();
            int rand = lowerBound + random.nextInt(Integer.MAX_VALUE) % (upperBound - lowerBound + 1);
            Character newChar = (char) rand;
            sb.append(newChar);
        }

        return String.format("%s/%s", baseDomain, sb.toString());
    }

    private static boolean validate(String urlStr) {
        try {
            new URL(urlStr);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }


}
