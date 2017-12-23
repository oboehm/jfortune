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
    private final Fortune fortune = new Fortune();

    /**
     * Test method for {@link Fortune#getCookie()}.
     */
    @Test
    public void getFortuneProvider() {
        Cookie cookie = fortune.getCookie();
        assertNotNull(cookie);
        LOG.info(cookie);
    }

    /**
     * Test method for {@link Fortune#main(String[])}.
     *
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Test
    public void testMain() throws UnsupportedEncodingException {
        PrintStream stdout = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            Fortune.main(new String[0]);
            String fortune = buffer.toString("UTF-8");
            LOG.info(fortune);
            assertThat(fortune, is(notNullValue()));
        } finally {
            System.setOut(stdout);
        }
    }

}
