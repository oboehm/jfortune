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
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link Main} class.
 *
 * @author oboehm
 * @since 0.5 (22.12.2017)
 */
public class MainTest {

    private static Logger LOG = LogManager.getLogger(MainTest.class);

    /**
     * Test method for {@link Main#main(String[])}.
     *
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Test
    public void testMain() throws UnsupportedEncodingException {
        PrintStream stdout = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            Main.main(new String[0]);
            String fortune = buffer.toString("UTF-8");
            LOG.info(fortune);
            assertThat(fortune, is(notNullValue()));
        } finally {
            System.setOut(stdout);
        }
    }

}
