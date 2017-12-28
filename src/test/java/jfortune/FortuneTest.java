/*
 * Copyright (c) 2017 by Oliver Boehm
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
 * (c)reated 22.12.2017 by oboehm (boehm@javatux.de)
 */
package jfortune;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link Fortune} class.
 *
 * @author oboehm
 * @since 0.5 (22.12.2017)
 */
public class FortuneTest {

    private static Logger LOG = LogManager.getLogger(FortuneTest.class);
    private final Fortune fortune = new Fortune(Locale.GERMAN);

    /**
     * Test method for {@link Fortune#getCookie(String...)}.
     */
    @Test
    public void getCookieDefault() {
        Cookie cookie = fortune.getCookie();
        assertNotNull(cookie);
        LOG.info(cookie);
    }

    /**
     * Test method for {@link Fortune#getCookie(String...)} with one name
     * for a CookieProvider.
     */
    @Test
    public void getCookieLiterature() {
        Cookie cookie = fortune.getCookie("literature");
        assertThat(cookie, is(notNullValue()));
    }

    /**
     * Test method for {@link Fortune#main(String[])}.
     */
    @Test
    public void testMain() {
        String fortune = callMain();
        LOG.info(fortune);
        assertThat(fortune, is(notNullValue()));
    }

    /**
     * This is the test for the help option ("-h".
     */
    @Test
    public void testHelp() {
        String help = callMain("-h");
        assertThat(help, containsString("help"));
        LOG.info(help);
    }

    private static String callMain(String... args) {
        PrintStream stdout = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            Fortune.main(args);
            return buffer.toString("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UTF-8 is not supported", ex);
        } finally {
            System.setOut(stdout);
        }
    }

}
