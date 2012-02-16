package info.cmlubinski.scala_migrations_example.example4

import com.imageworks.migration._

class Migrate_20120216180140_ForeignKey extends Migration {
  def up() {
    createTable("user_data"){
      _.bigint("id", NotNull, PrimaryKey)
    }
    createTable("account") {
      _.bigint("user", NotNull, PrimaryKey)
      _.bigint("balance", NotNull, Default("0"))
    }
    addForeignKey(on("account", "user"), references("user", "id"), OnDelete(Cascade))
    addConstraint(on("account", "balance"), "balance >= 0")
  }
  def down() {
    dropTable("account")
    dropTable("user_data")
  }
}
