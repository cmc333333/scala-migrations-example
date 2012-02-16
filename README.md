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

The core method here is `createTable()`, which takes a table name and a anonymous function as its parameters:

```scala
createTable("city"){ t =>
  t.varchar("name")
    .bigint("population")
    .boolean("rail")
    .blob("photo")
}
```
The argument to the function represents the table itself. This is a very clean method of describing the table and the
library will translate the columns into the proper column types.

There is also a corresponding `dropTable()` method in the Migration class.

##  Options

The library includes the concept of a list of "[Options][4]" when creating or modifying columns, indexes, 
constraints, etc. These are generally added to the list of parameters for the method used.

### Column Options
* Default - takes a string as its argument -- this string is raw SQL and is used to set a column default
* Limit - used for columns of a specific size (e.g. char); parameter is an int or a string (string being raw SQL)
* NotNull
* Nullable
* PrimaryKey
* Unique
* Precision & Scale - used for decimal fields
* Check/NamedCheck - add a constraint to a column

```scala
createTable("user_data"){
  _.bigint("id", NotNull, PrimaryKey)
   .varchar("name", Limit(100), Default("'lamer'"), NotNull)
   .varchar("email", Unique)
   .integer("age", Nullable, Check("age > 17"))
}
```
### Grant Privilege Types
* AllPrivileges
* DeletePrivilege
* InsertPrivilege
* TriggerPrivilege
* ReferencesPrivilege - Can be applied to specific columns
* SelectPrivilege - Can be applied to specific columns
* UpdatePrivilege - Can be applied to specific columns

### Foreign Key Option
* Name
* OnUpdate - Cascade, NoAction, Restrict, SetDefault, SetNull
* OnDelete - Cascade, NoAction, Restrict, SetDefault, SetNull

### Index Options
* Name - Defines a named index
* Unique - Guarantee that the indexed values are unique

### Check Options
* Name - Defines the constraint's name

## Foreign Keys and Constraints

Adding foreign Keys and constraints after column creation uses a slightly different syntax. Instead of saying 
something like `addForeignKey("account.user", "user.id")`, you must use the `on()` and `references()` methods. For a 
foreign key, this means `addForeignKey(on("account", "user"), references("user", "id"))`. For a constraint, it would 
be something like `addCheck(on("account", "balance"), "balance >= 0")`.

## Analysis

Scala-migrations is by no means a complete library, but it certainly has a lot of value. It fills in a hole left by
advanced relational DSLs such as squeryl relating to database maintenance. Scala-migrations tries hard to keep a Scala
mindset, with heavy use of case classes, pattern matching, and closures. It still has a food in the Java world,
though, with an unfortunately over-reliance on Java sql constructs (including prepared statements and result sets,)
which make certain operations needlessly verbose.

[0]: http://cmlubinski.info "CM Lubinski"
[1]: https://github.com/cmc333333/scala-migrations-example "Github"
[2]: http://code.google.com/p/scala-migrations/
[3]: http://code.google.com/p/scala-migrations/source/browse/src/main/scala/com/imageworks/migration/SqlType.scala
[4]: http://code.google.com/p/scala-migrations/source/browse/src/main/scala/com/imageworks/migration/Options.scala
