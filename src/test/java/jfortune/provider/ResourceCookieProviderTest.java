/*
 * Created on May 16, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package jfortune.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author oliver
 */
public class ResourceCookieProviderTest {
    
	private static final Logger LOG = LogManager.getLogger(ResourceCookieProviderTest.class);
    private static final ResourceCookieProvider provider = new ResourceCookieProvider();

    @Test
    public void testInitFortuneProvider() {
        assertTrue("to less fortunes", provider.getNumberOfSayings() > 0);
    }

    @Test
    public void testFortuneProvider() throws IOException {
        String s = provider.getSaying(0);
        assertTrue("string too short", s.length() > 1);
        s = provider.getSaying(2);
        assertTrue("string too short", s.length() > 1);
    }

    @Test
    public void testFortuneProviderString() throws IOException {
    	ResourceCookieProvider fortunes = new ResourceCookieProvider();
    	String s = fortunes.getSaying(10);
    	LOG.debug(s);
    }
    
    public void donttestFortuneProviderNull() throws IOException {
    	ResourceCookieProvider fortunes = new ResourceCookieProvider(null);
    	String s = fortunes.getSaying();
    	LOG.debug(s);
    }

}
