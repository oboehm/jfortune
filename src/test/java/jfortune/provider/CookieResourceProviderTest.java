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

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author oliver
 */
public class CookieResourceProviderTest {
    
	private static final Logger LOG = LogManager.getLogger(CookieResourceProviderTest.class);
    private static final CookieResourceProvider provider = new CookieResourceProvider();

    @Test
    public void testInitFortuneProvider() {
        assertTrue("to less fortunes", provider.getNumberOfSayings() > 0);
    }

    @Test
    public void testFortuneProvider() {
        String s = provider.getSaying(0);
        assertTrue("string too short", s.length() > 1);
        s = provider.getSaying(2);
        assertTrue("string too short", s.length() > 1);
    }

    @Test
    public void testFortuneProviderString() {
    	CookieResourceProvider fortunes = new CookieResourceProvider();
    	String s = fortunes.getSaying(10);
    	LOG.debug(s);
    }

    /**
     * During testing of German cookies some encoding problems with umlaute
     * appears.
     */
    @Test
    public void testUmlaute() {
        CookieResourceProvider provider = new CookieResourceProvider("umlaut");
        String cookie = provider.getCookie().getText().trim();
        assertThat(cookie, anyOf(equalTo("R\u00fcckvergr\u00f6\u00dferungsger\u00e4t"),
                equalTo("TR\u00c4NEN\u00dcBERSTR\u00d6MT")));
    }

}
