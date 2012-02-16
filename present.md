# Scala-Migrations -- an Overview

[CM Lubinski][0]

02/16/12

[Github][1]

---

## Key Concepts

* Migration
* Migrator

## Migrator

* InstallAllMigrations
* RemoveAllMigrations
* MigrateToVersion
* RollbackMigration

## Migration Overview

```
Migrate_\d+_[a-zA-Z0-9]*
```
`date -u +%Y%m%d%H%M%S`

## Basic Migration Methods

* execute
* addColumn
* alterColumn
* addIndex

* removeIndex
* removeColumn

## Tables

##  Options

### Column Options
* Default
* Limit
* NotNull
* Nullable
* PrimaryKey
* Unique
* Precision & Scale
* Check/NamedCheck

### Grant Privilege Types
* AllPrivileges
* DeletePrivilege
* InsertPrivilege
* TriggerPrivilege
* ReferencesPrivilege - Can be applied to specific columns
* SelectPrivilege - Can be applied to specific columns
* UpdatePrivilege - Can be applied to specific columns

## Foreign Keys and Constraints

## Analysis

[0]: http://cmlubinski.info "CM Lubinski"
[1]: https://github.com/cmc333333/scala-migrations-example "Github"
[2]: http://code.google.com/p/scala-migrations/
[3]: http://code.google.com/p/scala-migrations/source/browse/src/main/scala/com/imageworks/migration/SqlType.scala
[4]: http://code.google.com/p/scala-migrations/source/browse/src/main/scala/com/imageworks/migration/Options.scala
