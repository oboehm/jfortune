/*
 * Copyright (c) 2017-2026 by Oliver Boehm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated May 16, 2003 by oboehm (boehm@javatux.de)
 */
package jfortune.provider;

import jfortune.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author oliver
 */
class CookieResourceProviderTest {
    
	private static final Logger log = LogManager.getLogger(CookieResourceProviderTest.class);
    private final CookieResourceProvider provider = new CookieResourceProvider();

    @Test
    void testInit() {
        assertThat(provider.getNumberOfCookies(), is(greaterThan(1)));
    }

    /**
     * During testing of German cookies some encoding problems with umlaute
     * appears.
     */
    @Test
    void testUmlaute() {
        CookieResourceProvider provider = new CookieResourceProvider("test/umlaut");
        assertThat(provider.getSources(), contains("test/umlaut"));
        String cookie = provider.getCookie().getText().trim();
        assertThat(cookie, anyOf(equalTo("R\u00fcckvergr\u00f6\u00dferungsger\u00e4t"),
                equalTo("TR\u00c4NEN\u00dcBERSTR\u00d6MT")));
    }

    /**
     * Two cookies received with the same (random) number should be equals.
     */
    @Test
    void testGetCookie() {
        int n = (int) System.currentTimeMillis();
        Cookie one = provider.getCookie(n);
        Cookie two = provider.getCookie(n);
        assertEquals(one, two);
    }

    /**
     * Test method for {@link CookieResourceProvider#getShortCookie()}.
     */
    @Test
    void testGetShortCookie() {
        Cookie cookie = provider.getShortCookie();
        log.info(cookie);
        assertThat(cookie.length(), lessThanOrEqualTo(provider.getShortLength()));
    }

    /**
     * Test method for {@link CookieResourceProvider#getLongCookie()}.
     */
    @Test
    void testGetLongCookie() {
        Cookie cookie = provider.getLongCookie();
        assertThat(cookie.length(), greaterThanOrEqualTo(provider.getShortLength()));
    }

    /**
     * Test method for {@link CookieResourceProvider#getShortCookie(int)}.
     */
    @Test
    void testGetShortCookieInt() {
        int n = (int) System.currentTimeMillis();
        Cookie one = provider.getShortCookie(n);
        Cookie two = provider.getShortCookie(n);
        assertEquals(one, two);
        log.info(one);
    }

    /**
     * Test method for {@link CookieResourceProvider#getLongCookie(int)}.
     */
    @Test
    void testGetLongCookieInt() {
        int n = (int) System.currentTimeMillis();
        Cookie one = provider.getLongCookie(n);
        Cookie two = provider.getLongCookie(n);
        assertEquals(one, two);
    }

    /**
     * Test method for {@link CookieResourceProvider#CookieResourceProvider(String...)}.
     */
    @Test
    void testCookieResourceProviderString() {
        CookieResourceProvider literature = new CookieResourceProvider("en/literature");
        assertThat(literature.getNumberOfCookies(), is(greaterThan(1)));
    }

    /**
     * Test mehthod for {@link CookieResourceProvider#CookieResourceProvider(Locale, String...)}.
     */
    @Test
    void testGermanCookies() {
        CookieResourceProvider deProvider = new CookieResourceProvider(Locale.GERMAN);
        assertThat(deProvider.getSources(), hasItem("de/computer"));
        log.info(deProvider.getShortCookie());
    }

    /**
     * If {@link CookieResourceProvider#CookieResourceProvider(Locale, String...)}
     * is called with a non existing country the default cookies should be
     * loaded.
     */
    @Test
    void testWrongCountry() {
        CookieResourceProvider stateless = new CookieResourceProvider(Locale.CANADA);
        assertThat(stateless.getSources(), not(emptyCollectionOf(String.class)));
    }

    /**
     * Test method for {@link jfortune.CookieProvider#setShortLength(int)}.
     */
    @Test
    void testSetShortLength() {
        provider.setShortLength(80);
        Cookie cookie = provider.getShortCookie();
        assertThat(cookie.length(), is(lessThanOrEqualTo(80)));
    }

}
