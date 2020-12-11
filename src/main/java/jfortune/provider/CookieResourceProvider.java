/*
 * Created on May 16, 2003 by boehm@2xp.de
 */
package jfortune.provider;

import jfortune.Cookie;
import jfortune.CookieProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patterntesting.runtime.monitor.ResourcepathMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This implementation of the {@link CookieProvider} interface gets its cookies
 * from resources. If you want to extend the provides sayings put the resources
 * in the classpath and place it in the fortunes folder (this is the starting
 * point for cookie resources).
 * <p>
 * The format of the sayings is the same as the source format for the original
 * fortune program. The single sayings are divide by empty lines with a '%" at
 * the beginning.
 * </p>
 *
 * @author oliver
 */
public class CookieResourceProvider implements CookieProvider {

    private static final Logger LOG = LogManager.getLogger(CookieResourceProvider.class);
    private static final String FORTUNES_FOLDER = "/fortunes/";
    private final Map<String, List<String>> cookies = new HashMap<>();
    private Random random = new Random();
    private int shortLength = 160;

    /**
     * Instantiates the class with the given names. As names you can use
     * "fortunes" or "literature". If it is used as default constructor with
     * no arguments the resource "mixed" will be used as default.
     *
     * @param names names of the resource, e.g. "en/fortunes"
     */
    public CookieResourceProvider(String... names) {
        if (names.length == 0) {
            cookies.put("mixed", readSayings("mixed"));
        } else {
            Arrays.stream(names).forEach(s -> cookies.put(s, readSayings(s)));
        }
    }

    /**
     * The default cookies are English mixed with some other languages. If you
     * want cookies for other languages you must set it here.
     * <p>
     * NOTE: in v0.5 only {@link Locale#GERMAN} is supported as additional
     * language whereas the default cookies are in English (most of them).
     * </p>
     *
     * @param language e.g. {@link Locale#GERMAN}
     * @param names    names of the resource, e.g. "fortunes"
     */
    public CookieResourceProvider(Locale language, String... names) {
        String prefix = FORTUNES_FOLDER + language.getLanguage() + "/";
        ResourcepathMonitor resourceMon = ResourcepathMonitor.getInstance();
        for (String rsc : resourceMon.getResources()) {
            if (rsc.startsWith(prefix)) {
                String fortuneCollection = rsc.substring(prefix.length());
                String name = rsc.substring(FORTUNES_FOLDER.length());
                if (names.length != 0) {
                    List<Object> fortuneBooks = Arrays.asList(Arrays.stream(names).toArray());
                    if (fortuneBooks.contains(fortuneCollection)) {
                        cookies.put(name, readSayings(language, name));
                    }
                } else {
                    cookies.put(name, readSayings(language, name));
                }
            }
        }
    }

    private static List<String> readSayings(Locale lang, String name) {
        String resource = lang.getLanguage() + "/" + name;
        if (CookieResourceProvider.class.getResource(FORTUNES_FOLDER + resource) == null) {
            resource = name;
        }
        return readSayings(resource);
    }

    private static List<String> readSayings(String resource) {
        String from = FORTUNES_FOLDER + resource;
        List<String> sayings = new ArrayList<>();
        LOG.trace("Reading fortunes from {}...", from);
        try (InputStream istream = CookieResourceProvider.class.getResourceAsStream(from)) {
            if (istream == null) {
                throw new IOException("resource '" + from + "' not found");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream, StandardCharsets.UTF_8));
            String s = readSaying(reader);
            while (StringUtils.isNotBlank(s)) {
                sayings.add(s);
                s = readSaying(reader);
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("cannot reed cookies from " + from);
        }
        LOG.debug("Reading fortunes from {} finished with {} entries.", from, sayings.size());
        return sayings;
    }

    private static String readSaying(BufferedReader reader) throws IOException {
        StringBuilder cookie = new StringBuilder();
        String line = reader.readLine();
        while (line != null && !line.startsWith("%")) {
            cookie.append(line);
            cookie.append('\n');
            line = reader.readLine();
        }
        return cookie.toString().trim();
    }

    /**
     * Gets the length of fortune length considered to be "short":
     *
     * @return length in characters (default is 160)
     */
    @Override
    public int getShortLength() {
        return this.shortLength;
    }

    /**
     * Set the longest fortune length (in characters) considered to be "short"
     * (the default is 160). All fortunes longer than this are considered "long".
     *
     * @param n length
     */
    @Override
    public void setShortLength(int n) {
        this.shortLength = n;
    }

    /**
     * Returns the set of sources where the cookies comes from.
     *
     * @return set of sources
     */
    public Set<String> getSources() {
        return this.cookies.keySet();
    }

    /**
     * This method allows you to control the 'randomness' of the cookies. E.g.
     * you can use a {@link Random} with a defined seed to get the same cookies
     * each time. This can be helpful for testing.
     *
     * @param random a random number generator
     */
    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Returns random, hopefully interesting, adage.
     *
     * @return a fortune
     * @since 0.5
     */
    @Override
    public Cookie getCookie() {
        int n = Math.abs(random.nextInt() % this.getNumberOfCookies());
        return new Cookie(getSaying(n));
    }

    private String getSaying(int n) {
        return getSayings().get(n);
    }

    /**
     * Calculates the number of cookies.
     *
     * @return number of cookiess
     */
    public int getNumberOfCookies() {
        return getSayings().size();
    }

    private List<String> getSayings() {
        List<String> sayings = new ArrayList<>();
        for (List<String> cookieList : cookies.values()) {
            sayings.addAll(cookieList);
        }
        return sayings;
    }

}
