/*
 * Created on May 16, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package jfortune.provider;

import jfortune.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author oliver
 */
public class CookieResourceProviderTest {
    
	private static final Logger LOG = LogManager.getLogger(CookieResourceProviderTest.class);
    private static final CookieResourceProvider provider = new CookieResourceProvider();

    @Test
    public void testInit() {
        assertThat(provider.getNumberOfSayings(), is(greaterThan(1)));
    }

    /**
     * During testing of German cookies some encoding problems with umlaute
     * appears.
     */
    @Test
    public void testUmlaute() {
        CookieResourceProvider provider = new CookieResourceProvider("test/umlaut");
        String cookie = provider.getCookie().getText().trim();
        assertThat(cookie, anyOf(equalTo("R\u00fcckvergr\u00f6\u00dferungsger\u00e4t"),
                equalTo("TR\u00c4NEN\u00dcBERSTR\u00d6MT")));
    }

    /**
     * Two cookies received with the same (random) number should be equals.
     */
    @Test
    public void testGetCookie() {
        int n = (int) System.currentTimeMillis();
        Cookie one = provider.getCookie(n);
        Cookie two = provider.getCookie(n);
        assertEquals(one, two);
        LOG.info(one);
    }

    /**
     * Test method for {@link CookieResourceProvider#getShortCookie()}.
     */
    @Test
    public void testGetShortCookie() {
        Cookie cookie = provider.getShortCookie();
        LOG.info(cookie);
        assertThat(cookie.length(), lessThanOrEqualTo(provider.getShortLength()));
    }

    /**
     * Test method for {@link CookieResourceProvider#getLongCookie()}.
     */
    @Test
    public void testGetLongCookie() {
        Cookie cookie = provider.getLongCookie();
        assertThat(cookie.length(), greaterThanOrEqualTo(provider.getShortLength()));
    }

    /**
     * Test method for {@link CookieResourceProvider#CookieResourceProvider(String)}.
     */
    @Test
    public void testCookieResourceProviderString() {
        CookieResourceProvider literature = new CookieResourceProvider("literature");
        assertThat(literature.getNumberOfSayings(), is(greaterThan(1)));
    }

}
