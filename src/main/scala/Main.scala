package info.cmlubinski.scala_migrations_example
//  Migrator Setup

import org.postgresql.ds.PGSimpleDataSource
import com.imageworks.migration._

object Main {
  lazy val migrator = {
    val driverClass = "org.postgresql.Driver"
    val vendor = Vendor.forDriver(driverClass)
    val migrationAdapter = DatabaseAdapter.forVendor(vendor, None)
    val dataSource = new PGSimpleDataSource()
    dataSource.setServerName("localhost")
    dataSource.setDatabaseName("migration")
    dataSource.setUser("migration")
    dataSource.setPassword("migration")

    new Migrator(dataSource, migrationAdapter)
  }
  def apply(packageName:String) {
    //  migrator.migrate(InstallAllMigrations, "package.name.here", false)
    //  migrator.migrate(RemoveAllMigrations, "package.name.here", false)
    //  migrator.migrate(MigrateToVersion(20120214163244), "package.name.here", false)
    //  migrator.migrate(RollbackMigration(2), "package.name.here", false)
    
    migrator.migrate(InstallAllMigrations, "info.cmlubinski.scala_migrations_example." + packageName, false)
  }
}
