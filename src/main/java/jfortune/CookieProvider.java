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

import java.util.Set;

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
     * Short cookies has a length of maxmimal 160 characters. If you wants
     * another maximal length for short cookies use
     * {@link #setShortLength(int)} to set it.
     *
     * @return a cookie of maximal 160 characters
     */
    default Cookie getShortCookie() {
        Cookie cookie = getCookie();
        if (cookie.length() <= getShortLength()) {
            return cookie;
        } else {
            return getShortCookie();
        }
    }

    /**
     * Long cookies has a length of more than 160 characters. If you wants
     * another minimal length for short cookies use
     * {@link #setShortLength(int)} to set it.
     *
     * @return a cookie more than 160 characters
     */
    default Cookie getLongCookie() {
        Cookie cookie = getCookie();
        if (cookie.length() >= getShortLength()) {
            return cookie;
        } else {
            return getLongCookie();
        }
    }

    /**
     * Returns a cookie which belongs to the given random. I.e. the next call
     * with the same random value will return the same cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    Cookie getCookie(int random);

    /**
     * Returns a short cookie which belongs to the given random. I.e. the next
     * call to this method will return the same short cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    default Cookie getShortCookie(int random) {
        Cookie cookie = getCookie(random);
        if (cookie.length() > getShortLength()) {
            return getShortCookie(random + 1);
        }
        return cookie;
    }

    /**
     * Returns a long cookie which belongs to the given random. I.e. the next
     * call to this method will return the same long cookie.
     *
     * @param random whole range of 'int' is allowed as value
     * @return a cookie
     */
    default Cookie getLongCookie(int random) {
        Cookie cookie = getCookie(random);
        if (cookie.length() > getShortLength()) {
            return cookie;
        }
        return getLongCookie(random + 1);
    }

    /**
     * Set the longest fortune length (in characters) considered to be "short"
     * (the default is 160). All fortunes longer than this are considered "long".
     *
     * @param n length
     */
    default void setShortLength(int n) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the length of fortune length considered to be "short":
     *
     * @return length in characters (default is 160)
     */
    default int getShortLength() {
        return 160;
    }

    /**
     * Returns the set of sources where the cookies comes from.
     * This can be a list of resources, a list of files or list of URLs,
     * depending on the provider.
     *
     * @return set of sources
     */
    Set<String> getSources();

}
