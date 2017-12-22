/*
 * Created on May 16, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package jfortune.provider;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author oliver
 */
public class ResourceFortuneProviderTest extends TestCase {
    
	static private Logger log = LogManager.getLogger(ResourceFortuneProviderTest.class);
    static private ResourceFortuneProvider provider = null;

    /**
     * This method is called before every test.
     * @see TestCase#setUp()
     */
    public void setUp() {
        if (provider == null) {
            provider = new ResourceFortuneProvider();
        }
    }
    
    public void testInitFortuneProvider() {
        assertTrue("to less fortunes", provider.getNumberOfSayings() > 0);
    }
    
    public void testFortuneProvider() throws IOException {
        String s = provider.getSaying(0);
        assertTrue("string too short", s.length() > 1);
        s = provider.getSaying(2);
        assertTrue("string too short", s.length() > 1);
    }
    
    public void testFortuneProviderString() throws IOException {
    	ResourceFortuneProvider fortunes = new ResourceFortuneProvider("/fortune/fortunes");
    	String s = fortunes.getSaying(10);
    	log.debug(s);
    }
    
    public void donttestFortuneProviderNull() throws IOException {
    	ResourceFortuneProvider fortunes = new ResourceFortuneProvider(null);
    	String s = fortunes.getSaying();
    	log.debug(s);
    }

}
