/*
 * Created on May 16, 2003 by boehm@2xp.de
 */
package jfortune.provider;

import jfortune.Cookie;
import jfortune.CookieProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author oliver
 */
public class CookieResourceProvider implements CookieProvider {

    private static final Logger LOG = LogManager.getLogger(CookieResourceProvider.class);
    private final Map<String, List<String>> cookies = new HashMap<>();
    private Random random = new Random();

    /**
     * Default constructor for default cookies.
     */
    public CookieResourceProvider() {
    	this("fortunes");
    }

    /**
     * Instantiates the class with the given name.
     *
     * @param name name of the resource, e.g. "fortunes"
     */
    public CookieResourceProvider(String name) {
        cookies.put(name, readSayings(name));
    }

    /**
     * The sources you can set is a list of resources. These resources contains
     * the sources for the cookies.
     *
     * @param names name of the cookie resources
     */
    public void setSources(String... names) {
        if (names.length == 0) {
            return;
        }
        addNewSources(names);
        removeOldSources(names);
    }

    private void addNewSources(String[] names) {
        for (String name : names) {
            List<String> sayings = cookies.get(name);
            if (sayings == null) {
                cookies.put(name, readSayings(name));
            }
        }
    }

    private void removeOldSources(String[] names) {
        Set<String> keys = new HashSet<>(cookies.keySet());
        keys.removeAll(Arrays.asList(names));
        for (String name : keys) {
            cookies.remove(name);
        }
    }

    /**
     * Returns random, hopefully interesting, adage.
     *
     * @return a fortune
     * @since 0.5
     */
    @Override
    public Cookie getCookie() {
        return getCookie(random.nextInt());
    }

    /**
     * Returns a cookie which belongs the given random. I.e. the next call
     * with the same random value will return the same cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    @Override
    public Cookie getCookie(int random) {
        int n = Math.abs(random % this.getNumberOfCookies());
        return new Cookie(getSaying(n));
    }

    /**
     * Returns a short cookie which belongs to the given random. I.e. the next
     * call to this method will return the same short cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    @Override
    public Cookie getShortCookie(int random) {
        Cookie cookie = getCookie(random);
        if (cookie.length() > getShortLength()) {
            return getShortCookie(random + 1);
        }
        return cookie;
    }

    /**
     * Returns a long cookie which belongs to the given random. I.e. the next
     * call to this method will return the same long cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    @Override
    public Cookie getLongCookie(int random) {
        Cookie cookie = getCookie(random);
        if (cookie.length() > getShortLength()) {
            return cookie;
        }
        return getLongCookie(random + 1);
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
        Set<Map.Entry<String, List<String>>> entries = cookies.entrySet();
        for (List<String> cookieList : cookies.values()) {
            sayings.addAll(cookieList);
        }
        return sayings;
    }
    
    private List<String> readSayings(String name) {
        String from = "/fortunes/" + name;
        List<String> sayings = new ArrayList<>();
        LOG.debug("Reading fortunes from {}...", from);
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
    
}
