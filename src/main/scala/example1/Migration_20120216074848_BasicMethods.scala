package info.cmlubinski.scala_migrations_example.example1

import com.imageworks.migration._

class Migrate_20120216074848_BasicMethods extends Migration {
  def up() {
    execute("CREATE TABLE dogs (name varchar)")
    
    addColumn("dogs", "color", VarcharType)
    addColumn("dogs", "pups", DecimalType)

    alterColumn("dogs", "pups", SmallintType)

    addIndex("dogs", "color")
    addIndex("dogs", Array("color", "pups"))
  }

  def down() {
    removeIndex("dogs", Array("color", "pups"))
    removeIndex("dogs", "color")

    removeColumn("dogs", "pups")

    removeColumn("dogs", "color")
    execute("DROP TABLE dogs")
  }
}
