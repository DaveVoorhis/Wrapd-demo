Wrapd-demo
==========

Wrapd is a *SQL amplifier*, a lightweight database abstraction layer generator and schema migrator that helpfully exposes
SQL in Java rather than hiding it.

See https://wrapd.org for Wrapd documentation.

The Wrapd library is hosted at https://github.com/DaveVoorhis/Wrapd.

This is a simple sample application created using Wrapd. It consists of four subprojects:

1. _schema_ - An illustration of automated schema management.
2. _queries_ - An illustration of auto-generating the database abstraction layer from SQL queries.
3. _database_ - Shared database connection information.
4. _application_ - A simple application that uses _database_ and _queries_ to update and query the database.

### To build and run the Wrapd demo ###

You either need Docker installed on your system to use the provided MySQL configuration,
or you need an accessible MySQL server. If you're using the latter, change
database/src/main/java/org/reldb/wrapd/demo/mysql/Configuration.java as needed to
reflect your database settings. The database subproject tests will verify database
connectivity.

1. _docker-compose up -d_ (skip if using your own MySQL server)
2. _gradle clean_
3. _gradle runSchemaSetup_
4. _gradle runQueryBuild_
5. _gradle build_
6. _gradle run_
7. _docker-compose down -v_ (skip if using your own MySQL server)

### Documentation and examples are a work-in-progress. Wrapd will soon be available on Maven Central. Watch this space! ###
