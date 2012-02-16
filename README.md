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
```

[0]: http://cmlubinski.info "CM Lubinski"
[1]: https://github.com/cmc333333/scala-migrations-example "Github"
[2]: http://code.google.com/p/scala-migrations/
