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
import org.apache.commons.cli.*;

import java.util.*;

/**
 * This is the main entry point of the application.
 *
 * @author oboehm
 * @since 0.5 (22.12.2017)
 */
@SuppressWarnings("squid:S106")
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
     * {@link CookieProvider} is used but with the given names of the
     * resources. If you want all resources of a language use
     * {@link Fortune#Fortune(Locale, String...)}.
     *
     * @param names e.g. "fortunes", "literature"
     */
    public Fortune(String... names) {
        this(new CookieResourceProvider(names));
    }

    /**
     * Like the default constructor {@link Fortune#Fortune()} the default
     * {@link CookieProvider} is used but with language given as argument
     * In v0.5 only "de", "es" and "en" are provided as language but this may
     * change in the future.
     *
     * @param language e.g. {@link Locale#GERMAN}
     * @param names e.g. "fortunes", "literature"
     */
    public Fortune(Locale language, String... names) {
        this(new CookieResourceProvider(language, names));
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
     * Returns the {@link CookieProvider} which is used to provide the cookies.
     *
     * @return the {@link CookieProvider}
     */
    public CookieProvider getProvider() {
        return provider;
    }

    /**
     * It generates a random epigram.
     *
     * @return a random cookie
     */
    public Cookie getCookie() {
        return provider.getCookie();
    }

    /**
     * It generates a short random epigram.
     *
     * @return a random cookie
     */
    public Cookie getShortCookie() {
        return provider.getShortCookie();
    }

    /**
     * It generates a long random epigram.
     *
     * @return a random cookie
     */
    public Cookie getLongCookie() {
        return provider.getLongCookie();
    }

    /**
     * Set the longest fortune length (in characters) considered to be "short"
     * (the default is 160). All fortunes longer than this are considered "long".
     *
     * @param n length
     * @since 0.6
     */
    public void setShortLength(int n) {
        provider.setShortLength(n);
    }

    /**
     * This method allows you to control the 'randomness' of the cookies.
     * Setting the same seed brings you the same "random" sequence of cookies.
     *
     * @param seed seed of the used random number generator
     * @since 0.6
     */
    public void setRandom(long seed) {
        setRandom(new Random(seed));
    }

    /**
     * This method allows you to control the 'randomness' of the cookies. E.g.
     * you can use a {@link Random} with a defined seed to get the same cookies
     * each time. This can be helpful for testing.
     *
     * @param random a random number generator
     * @since 0.6
     */
    public void setRandom(Random random) {
        provider.setRandom(random);
    }

    /**
     * Without an option it prints a cookie to stdout. If you want to want to
     * see the allowewd options use the option <tt>-h</tt> - this will print
     * a short help with the allowed options.
     * <p>
     * You can also call the
     * <a href="http://www.skrenta.com/rt/man/fortune.6.html">fortune(6)</a>
     * manuel to see the option. Most options are provided by the main program.
     * </p>
     *
     * @param args will be ignored.
     */
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "log4j2-jfortune.xml");
        int rc = run(args);
        System.exit(rc);
    }

    protected static int run(String[] args) {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption('h')) {
                printHelp(options);
                return 0;
            }
            String[] names = line.getArgs();
            Fortune fortune = new Fortune(names);
            if (line.hasOption('c')) {
                Locale lang = Locale.forLanguageTag(line.getOptionValue('c'));
                fortune = new Fortune(lang, names);
            }
            if (line.hasOption('f')) {
                print(fortune.getProvider().getSources());
                return 0;
            }
            if (line.hasOption('r')) {
                fortune.setRandom(Long.valueOf(line.getOptionValue('r')));
            }
            if (line.hasOption('n')) {
                fortune.setShortLength(Integer.valueOf(line.getOptionValue('n')));
            }
            if (line.hasOption('l')) {
                print(fortune.getLongCookie());
            } else if (line.hasOption('s')) {
                print(fortune.getShortCookie());
            } else {
                print(fortune.getCookie());
            }
            return 0;
        } catch (ParseException exp) {
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
            printHelp(options);
            return 1;
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "print this message");
        options.addOption("c", "country", true, "country or language");
        options.addOption("f", "file", false,
                "print out the list of files or resources which would be searched, but don't print a fortune");
        options.addOption("l", "long", false, "long dictums only");
        options.addOption("s", "short", false, "short apothegms only");
        options.addOption("n", true, "set the longest fortune length considered to be 'short'");
        options.addOption("r", "random", true, "seed for the random generator");
        return options;
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("fortune [name ...]", "Prints a random fortune cookie.", options,
                "For a more detail description ask Google with 'man fortune'.", true);
    }

    private static void print(Set<String> entries) {
        SortedSet<String> sorted = new TreeSet<>(entries);
        sorted.forEach(System.out::println);
    }

    private static void print(Cookie cookie) {
        System.out.println(cookie.getText());
    }

}
