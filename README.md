# Apache Tamaya (incubating) Sandbox

This is the sandbox of [Apache Tamaya](https://tamaya.incubator.apache.org).
The sandbox contains additional modules (extensions) 
of Apache Tamaya (incubating) **which are not mature enough** 
to be part of Apache Tamaya itself or the official extensions
of Apache Tamaya.

## Rules for sandbox modules

* All modules must be independent of each other.
* A module can be promoted by the PMC of Tamaya
  to be part of the official Tamaya extensions package if it is
  mature enough.
* A sandbox module should have it's own build chain
  in [Tamaya's Jenkins view](https://builds.apache.org/view/S-Z/view/Tamaya/)
  

## Building Apache Tamaya Sandbox

The Apache Tamaya project is built with [Maven 3](https://maven.apache.org/) and [Java 7](https://java.sun.com/), so you need JDK >=1.7 and a reasonable version of maven
installed on your computer.


Then you can build Tamaya by the following command:
```
$ export MAVEN_OPTS="-Xmx512m -XX:PermGenSpace=200m"
$ mvn
```