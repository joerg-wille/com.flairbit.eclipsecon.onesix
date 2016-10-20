# Pure maven bnd

This project demonstrate how to create a pure maven projects using bnd and the [bnd-maven-plugin](https://github.com/bndtools/bnd/tree/master/maven/bnd-maven-plugin).
This plugins allows building an OSGi enRoute executable JAR using Maven and vi only (or any other IDE of your choice).

The projects create a simple key-value store service based on Apache Cassandra. This service exposes a single API that stores text messages in Cassandra and assign them a UUID key.
The service will be available from the OSGi command line thanks to GOGO shell.

The workspace is made of the following projects:

* example.cassandra.parent - the parent pom project
* com.flairbit.example.cassandra.api - the API bundle
* com.flairbit.example.cassandra.cmd - the gogo command bundle
* com.flairbit.example.cassandra.impl - the service provider: implement the API defined in the API bundle
* com.flairbit.example.cassandra.itest - the integration test code
* example.cassandra.bndrun - bndrun project for the service: it bootstraps the OSGi runtime to resolve and run our kvs service
* example.cassandra.cmd.bndrun - this bndrun project adds the GOGO shell service to the OSGi runtime
* example.cassandra.itest.bndrun - the bndrun file for the integration-test execution

## Requirements
Install Apache Cassandra: the simplest way is to spin out Cassandra through docker:

	$ docker run --name some-cassandra -d cassandra:latest -p 9144:9144

Then connect to cassandra through cqlsh and execute the `com.flairbit.example.cassandra.api/cql-snapshot.cql` script.

There are no other requirements other than Java and Maven.

## Run the projects
Just run `mvn install` from the workspace root directory. To launch the cassandra KVS service just do

	java -jar example.cassandra.cmd.bndrun/com.flairbit.example.cassandra.jar