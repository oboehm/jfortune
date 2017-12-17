/*
 * Created on May 16, 2003 by boehm@2xp.de
 */
package jfortune.server;

import org.apache.log4j.Level;
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
public class FortuneProvider {
    
    private List sayings = null;
    private Random random = new Random();
    private static Logger log = Logger.getLogger(FortuneProvider.class);
   
    public FortuneProvider() {
    	this("/fortune/fortunes");
//    	sayings = new Vector();
//    	sayings.add("If we were able to understand it, we wouldn't call it code!");
//    	sayings.add("In C we had to code our own bugs. In C++ we can inherit them. (Prof. Gerald Karam)");
    }
    
    public FortuneProvider(String resource) {
        try {
            sayings = this.readSayings(resource);
        } catch (IOException ioe) {
            log.warn("can't read " + resource, ioe);
        }
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
//			if (s == null) {
//				break;
//			}
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
    
    public static void main(String[] args) throws InterruptedException {
    	log.setLevel(Level.OFF);
        FortuneProvider fortuneProvider = new FortuneProvider();
        int n = (args.length < 1) ? 1 : Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
        	Thread.sleep(1500);
            System.out.println(fortuneProvider.getSaying());
        }
    }

}
