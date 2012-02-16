package info.cmlubinski.scala_migrations_example.example4

import com.imageworks.migration._

class Migrate_20120216180140_ForeignKey extends Migration {
  def up() {
    createTable("person"){
      _.bigint("id", NotNull, PrimaryKey)
    }
    createTable("account") {
      _.bigint("person", NotNull, PrimaryKey)
       .bigint("balance", NotNull, Default("0"))
    }
    addForeignKey(on("account" -> "person"), references("person" -> "id"), OnDelete(Cascade))
    addCheck(on("account" -> "balance"), "balance >= 0")
  }
  def down() {
    dropTable("account")
    dropTable("person")
  }
}
