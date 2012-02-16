package info.cmlubinski.scala_migrations_example
//  Migrator Setup

import org.postgresql.ds.PGSimpleDataSource
import com.imageworks.migration._

object Example1 {
  def apply() {
    val driverClass = "org.postgresql.Driver"
    val vendor = Vendor.forDriver(driverClass)
    val migrationAdapter = DatabaseAdapter.forVendor(vendor, None)
    val dataSource = new PGSimpleDataSource()
    dataSource.setServerName("localhost")
    dataSource.setDatabaseName("migrations")
    dataSource.setUser("migrations")
    dataSource.setPassword("migrations")

    val migrator = new Migrator(dataSource, migrationAdapter)
    
    //  migrator.migrate(InstallAllMigrations, "package.name.here", false)
    //  migrator.migrate(RemoveAllMigrations, "package.name.here", false)
    //  migrator.migrate(MigrateToVersion(20120214163244), "package.name.here", false)
    //  migrator.migrate(RollbackMigration(2), "package.name.here", false)
  }
}
