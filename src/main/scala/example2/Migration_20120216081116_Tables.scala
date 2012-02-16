package info.cmlubinski.scala_migrations_example.example2

import com.imageworks.migration._

class Migrate_20120216081116_Tables extends Migration {
  def up() {
    createTable("city"){
      _.varchar("name")
       .bigint("population")
       .boolean("rail")
       .blob("photo")
    }
  }
  def down() {
    dropTable("city")
  }
}

