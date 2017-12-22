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

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests {@link FortuneProviderFactory} class.
 *
 * @author oboehm
 * @since 0.5 (22.12.2017)
 */
public class FortuneProviderFactoryTest {

    private final FortuneProviderFactory factory = new FortuneProviderFactory();

    /**
     * Test method for {@link FortuneProviderFactory#getFortuneProvider()}.
     */
    @Test
    public void getFortuneProvider() {
        FortuneProvider provider = factory.getFortuneProvider();
        assertNotNull(provider);
    }

}
