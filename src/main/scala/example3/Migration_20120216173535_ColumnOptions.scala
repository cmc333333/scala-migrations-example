package info.cmlubinski.scala_migrations_example.example3

import com.imageworks.migration._

class Migrate_20120216173535_ColumnOptions extends Migration {
  def up() {
    createTable("user_data"){
      _.bigint("id", NotNull, PrimaryKey)
       .varchar("name", Limit(100), Default("'lamer'"), NotNull)
       .varchar("email", Unique)
       .integer("age", Nullable, Check("age > 17"))
    }
  }
  def down() {
    dropTable("user_data")
  }
}
