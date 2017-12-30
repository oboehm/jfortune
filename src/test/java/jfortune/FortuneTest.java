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
import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
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
        callMain();
    }

    /**
     * This is the test for the help option ("-h").
     */
    @Test
    public void testHelp() {
        String help = callMain("-h");
        assertThat(help, containsString("help"));
    }

    /**
     * If an unknown option is given you should get also an help message.
     */
    @Test
    public void testUnknownOption() {
        String output = callMain("--unknown");
        assertThat(output, containsString("help"));
    }

    /**
     * With "-c" you can set the language.
     */
    @Test
    public void testCountryOption() {
        callMain("-c", "de");
    }

    /**
     * If we list the files for the Spanish version ("-c es"), we should see
     * one of the Spanish fortunes.
     */
    @Test
    public void testFileOption() {
        String output = callMain("-c", "es", "-f");
        assertThat(output, containsString("arte"));
    }

    /**
     * The option "-l" provides long fortunes.
     */
    @Test
    public void testLongOption() {
        String output = callMain("-l");
        assertThat(output.length(), greaterThanOrEqualTo(160));
    }

    /**
     * The option "-s" provides short fortunes.
     */
    @Test
    public void testShortOption() {
        String output = callMain("-s");
        assertThat(output.length(), lessThanOrEqualTo(160));
    }

    private static String callMain(String... args) {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
        try {
            Fortune.main(args);
            String output = outStream.toString("UTF-8");
            String error = errStream.toString("UTF-8");
            LOG.info("Output of 'main({})':\n{}{}", Arrays.toString(args), error, output);
            assertThat(output, is(notNullValue()));
            return output;
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UTF-8 is not supported", ex);
        } finally {
            System.setOut(stdout);
            System.setErr(stderr);
        }
    }

}
