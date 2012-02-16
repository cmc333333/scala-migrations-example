# Scala-Migrations -- an Overview

[CM Lubinski][0]

02/16/12

[Github][1]

---

## Key Concepts

* Migration - A small script that applies (or reverses) a set of changes to the database
* Migrator - Applies (or reverses) migrations in their order and keeps track of the current state of the database

## Migrator

First, the Migrator must be set up to use the right database connection. According to their project page,
[scala-migrations][2] runs on Derby, MySQL, Oracle, and PostgreSQL. Here's a simple Postgres setup:

```scala
import org.postgresql.ds.PGSimpleDataSource
import com.imageworks.migration._

val migrationAdapter = DatabaseAdapter.forVendor(Vendor.forDriver("org.postgresql.Driver"), None)
val dataSource = new PGSimpleDataSource()
dataSource.setServerName("localhost")
dataSource.setDatabaseName("migrations")
dataSource.setUser("migrations")
dataSource.setPassword("migrations")
val migrator = new Migrator(dataSource, migrationAdapter)
```

Next, you can call the `migrate` method, which has three parameters. The first parameter defines the type of migration
you want to perform:
* InstallAllMigrations - install all relevant migrations
* RemoveAllMigrations - remove all relevant migrations
* MigrateToVersion - adds all migrations before a particular version and drops all migrations after it
* RollbackMigration - way to rollback a specific number of migrations from the database' current position
The second and third parameter to the `migrate` method define where scala-migrations should be searching for
migrations. The second parameter is a string defining the root package name where migrations can be found. If the
third parameter is true, scala-migrations will search recursively in from the root package. The library uses something
akin to introspection to determine which migration classes it will need to instantiate.

## Migration Overview

Naming: Migration classes must follow a simple naming convention: 
```Migrate_\d+_[a-zA-Z0-9]*```
The number represents the migration "version," that is, the unique identifier for this migration. It is suggested that
you just use the current data and time, as provided by something like the date command:
```date -u +%Y%m%d%H%M%S```
The string following the version number generally conveys something brief about the migration, such as "ContactsTable"
or "EncryptedUserNames."

Every migration class must inherit from Migration and must implement the abstract `up()` and `down()` methods. The
`up()` method is called when a migration is applied; the `down()` when the migration is reversed. There are no
constraints on what you place inside these methods; they could do nothing or they could make some sophisticated
connection to an external service.

## Basic Migration Methods

* execute - Lowest common denominator, just execute this SQL string
* addColumn - Add a column of a given [type][3]
* alterColumn - Alter a column to match a given type (or other column options, see below)
* addIndex - Add an index to a column (or set of columns)

* removeIndex
* removeColumn

There are similar methods for adding and removing foreign keys, constrains, and permissions.

## Tables

The core method here is `createTable()`, which takes a tablename and a closure as its parameters:


* dropTable
* on
* references
* withPreparedStatement
* withResultSet
* createTable

Column options
index options


[0]: http://cmlubinski.info "CM Lubinski"
[1]: https://github.com/cmc333333/scala-migrations-example "Github"
[2]: http://code.google.com/p/scala-migrations/
[3]: http://code.google.com/p/scala-migrations/source/browse/src/main/scala/com/imageworks/migration/SqlType.scala
