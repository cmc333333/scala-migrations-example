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

[0]: http://cmlubinski.info "CM Lubinski"
[1]: https://github.com/cmc333333/scala-migrations-example "Github"
