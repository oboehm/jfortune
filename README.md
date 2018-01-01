[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.javatux.jfortune/jfortune/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.javatux.jfortune/jfortune)
[![Build Status](https://travis-ci.org/oboehm/jfortune.svg?branch=master)](https://travis-ci.org/oboehm/jfortune)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=de.javatux.jfortune:jfortune:master)](https://sonarcloud.io/dashboard?id=de.javatux.jfortune%3Ajfortune%3Amaster)
[![Coverage Status](https://coveralls.io/repos/github/oboehm/jfortune/badge.svg?branch=master)](https://coveralls.io/github/oboehm/jfortune)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# jfortune

`fortune` is a program from the early days of UNIX that produces fortune cookies.
The first version appears 1979 in [Unix Version 7](https://en.wikipedia.org/wiki/Version_7_Unix).
`jfortune` is a rewrite of the program in Java which can be defined as dependency for your own Java application.

The first version of `jfortune` was written in 2003 with Java 1.4, JUnit 3 and [Ant](http://ant.apache.org/).
It is now digged out, put on GitHub and delivered as v0.3 to the Central Maven Repo ([OSSRH-36625](https://issues.sonatype.org/browse/OSSRH-36625)).

With v0.5 this version was reworked, adapted to Java 8 and a command line interface added.
This version contains more fortunes but is not compatible with v0.3.

The input files for the embedded fortunes is a small selection from
[fortune-mod](https://github.com/shlomif/fortune-mod),
[fortunes-de](https://github.com/michaaa/fortunes-de) and
[fortunes-es](https://reposcope.com/package/fortunes-es)
which are delivered with Ubuntu.


## More Infos

* Project page: http://javatux.de/jfortune/
* Javadoc: http://javatux.de/jfortune/apidocs/index.html
* Development: [src/main/asciidoc/README](src/main/asciidoc/README.adoc)

---
January 2018,
Oli B.
