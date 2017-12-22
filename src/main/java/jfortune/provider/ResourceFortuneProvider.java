/*
 * Created on May 16, 2003 by boehm@2xp.de
 */
package jfortune.provider;

import jfortune.Fortune;
import jfortune.FortuneProvider;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @author oliver
 */
public class ResourceFortuneProvider implements FortuneProvider {
    
    private List sayings = null;
    private Random random = new Random();
    private static final Logger log = Logger.getLogger(ResourceFortuneProvider.class);
   
    public ResourceFortuneProvider() {
    	this("/fortune/fortunes");
    }
    
    public ResourceFortuneProvider(String resource) {
        try {
            sayings = this.readSayings(resource);
        } catch (IOException ioe) {
            log.warn("can't read " + resource, ioe);
        }
    }

    /**
     * Returns random, hopefully interesting, adage.
     *
     * @return a fortune
     * @since 0.5
     */
    @Override
    public Fortune getFortune() {
        return new Fortune(getSaying());
    }

    public String getSaying() {
        int n = random.nextInt(this.getNumberOfSayings());
        return getSaying(n);
    }
    
    public String getSaying(int n) {
        String s = (String) sayings.get(n);
        log.info("get saying " + n + ": " + s.substring(0, 20) + "...");
        return s;
    }
    
    public int getNumberOfSayings() {
        return sayings.size();
    }
    
    private List readSayings(String from) throws IOException {
        log.info("reading " + from + "...");
        InputStream istream = this.getClass().getResourceAsStream(from);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
				istream));
        List sayings = new Vector();
    	String s;
		do {
			s = readSaying(reader);
			sayings.add(s);
		} while (s != null);
        log.info("read: " + sayings.size() + " entries");
        istream.close();
        return sayings;
    }
    
    private String readSaying(BufferedReader reader) throws IOException {
        String saying = "";
        try {
            String line = reader.readLine();
            while (!line.startsWith("%")) {
                saying += line + "\n";
                line = reader.readLine();
            }
        } catch (NullPointerException ignored) {
        	return null;
        }
        return saying;
    }
    
}
