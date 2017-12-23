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
 * (c)reated 23.12.2017 by oboehm (boehm@javatux.de)
 */
package jfortune;

import org.junit.Test;
import patterntesting.runtime.junit.ObjectTester;

/**
 * Unit-Tests fuer {@link Cookie}-Klasse.
 *
 * @author oboehm
 * @since 0.5 (23.12.2017)
 */
public final class CookieTest {

    @Test
    public void testEquals() {
        Cookie one = new Cookie("one");
        Cookie anotherOne = new Cookie("one");
        ObjectTester.assertEquals(one, anotherOne);
    }

}
