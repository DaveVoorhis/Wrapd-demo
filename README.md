Wrapd-demo
==========

Wrapd is a *SQL amplifier*, a lightweight database abstraction layer generator and schema migrator that helpfully exposes
SQL in Java rather than hiding it.

See https://wrapd.org for Wrapd documentation.

The Wrapd library is hosted at https://github.com/DaveVoorhis/Wrapd.

This is a simple sample application created using Wrapd. It consists of four subprojects:

1. schema - An illustration of automated schema management.
2. queries - An illustration of auto-generating the database abstraction layer from SQL queries.
3. database - Shared database connection information.
4. application - A simple application that uses _database_ and _queries_ to update and query the database.

### To build and run the Wrapd demo ###

You either need Docker installed on your system to use the provided MySQL configuration,
or you need an accessible MySQL server. If you're using the latter, change
database/src/main/java/org/reldb/wrapd/demo/mysql/Configuration.java as needed to
reflect your database settings. The database subproject tests will verify database
connectivity.

```
docker-compose up -d   # skip if using your own MySQL server
gradle clean
gradle runSchemaSetup
gradle runQueryBuild
gradle build
gradle run
docker-compose down -v   # skip if using your own MySQL server
```

### Documentation and examples are a work-in-progress. Wrapd will soon be available on Maven Central. Watch this space! ###
