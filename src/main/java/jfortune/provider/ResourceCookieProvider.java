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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author oliver
 */
public class ResourceCookieProvider implements CookieProvider {

    private static final Logger LOG = LogManager.getLogger(ResourceCookieProvider.class);
    private final List<String> sayings = new ArrayList<>();
    private Random random = new Random();

    public ResourceCookieProvider() {
    	this("/fortunes/fortunes");
    }
    
    public ResourceCookieProvider(String resource) {
        try {
            readSayings(resource, sayings);
        } catch (IOException ioe) {
            LOG.warn("can't read " + resource, ioe);
        }
    }

    /**
     * Returns random, hopefully interesting, adage.
     *
     * @return a fortune
     * @since 0.5
     */
    @Override
    public Cookie getFortune() {
        return new Cookie(getSaying());
    }

    public String getSaying() {
        int n = random.nextInt(this.getNumberOfSayings());
        return getSaying(n);
    }
    
    public String getSaying(int n) {
        return sayings.get(n);
    }
    
    public int getNumberOfSayings() {
        return sayings.size();
    }
    
    private void readSayings(String from, List<String> sayings) throws IOException {
        LOG.debug("Reading fortunes from {}...", from);
        try (InputStream istream = ResourceCookieProvider.class.getResourceAsStream(from);
                BufferedReader reader = new BufferedReader(new InputStreamReader(istream))) {
            String s = readSaying(reader);
            while (StringUtils.isNotBlank(s)) {
                sayings.add(s);
                s = readSaying(reader);
            }
        }
        LOG.debug("Reading fortunes from {} finished with {} entries.", from, sayings.size());
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
