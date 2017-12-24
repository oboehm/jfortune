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
 * (c)reated 21.12.2017 by oboehm (ob@oasd.de)
 */
package jfortune;

/**
 * Interface CookieProvider.
 *
 * @author oboehm
 * @since 0.5 (21.12.2017)
 */
public interface CookieProvider {

    /**
     * The only thing a CookieProvider should provide is a fortune.
     *
     * @return a fortune cookie
     */
    Cookie getCookie();

    /**
     * Returns a cookie which belongs the given random. I.e. the next call
     * with the same random value will return the same cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    Cookie getCookie(int random);

    /**
     * Set the sources where the cookie provider should read its cookies,
     * This can be a list of resources, a list of files or list of URLs,
     * depending on the provider.
     *
     * @param names list of names
     */
    void setSources(String... names);

}
