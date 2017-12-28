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


import jfortune.provider.CookieResourceProvider;

import java.util.Locale;

/**
 * This is the main entry point of the application.
 *
 * @author oboehm
 * @since 0.5 (22.12.2017)
 */
public final class Fortune {

    private final CookieProvider provider;

    /**
     * The default constructor uses a default {@link CookieProvider} to get
     * later the cookies.
     */
    public Fortune() {
        this(new CookieResourceProvider());
    }

    /**
     * Like the default constructor {@link Fortune#Fortune()} the default
     * {@link CookieProvider} is used but with language given as argument.
     * In v0.5 only "de" and "en" are provide as language but this may change
     * in the future.
     *
     * @param language e.g. {@link Locale#GERMAN}
     */
    public Fortune(Locale language) {
        this(new CookieResourceProvider(language));
    }

    /**
     * If you write your own {@link CookieProvider} you can instantiate the
     * {@link Fortune} class with it. If not you can use the
     * {@link CookieResourceProvider}. Other providers may follow in the
     * future.
     *
     * @param provider e.g. {@link CookieResourceProvider}
     */
    public Fortune(CookieProvider provider) {
        this.provider = provider;
    }

    /**
     * It generates a random epigram. It uses the sources you give as argument.
     * If the argument is empty the default sources are used.
     *
     * @param names the names
     * @return a random cookie
     */
    public Cookie getCookie(String ... names) {
        provider.setSources(names);
        return provider.getCookie();
    }

    /**
     * Prints a cookie to stdout.
     *
     * @param args will be ignored.
     */
    @SuppressWarnings("squid:S106")
    public static void main(String[] args)  {
        System.out.println(new Fortune().getCookie());
    }

}
