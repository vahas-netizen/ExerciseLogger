package com.example.consciousworkouts.data

import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.migration.Migration
import androidx.sqlite.execSQL


@Database(
    version = 19,
    entities = [CompletedEntity::class, OptionEntity::class]
)

abstract class WorkoutDatabase() : RoomDatabase() {

    abstract fun getDao(): WorkoutDao


}


// Practicing writing simple database migrations

val MIGRATION_8_9 = Migration(8, 9) { connection ->
    connection.execSQL(
        "ALTER TABLE option_db ADD COLUMN dateAdded TEXT"
    )
}

val MIGRATION_9_10 = Migration(9, 10) { connection ->
    connection.execSQL(
        "CREATE TABLE test_db(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, randomWord TEXT NOT NULL)"
    )
}

val MIGRATION_10_11 = Migration(10, 11) { connection ->
    connection.execSQL(
        "DROP TABLE test_db"
    )
}

val MIGRATION_11_12 = Migration(11, 12) { connection ->
    connection.execSQL(
        "ALTER TABLE option_db DROP COLUMN dateAdded"
    )
}

val MIGRATION_12_13 = Migration(12, 13) { connection ->
    connection.execSQL(
        "CREATE TABLE another_db(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, testColumn TEXT NOT NULL, anotherTestColumn INTEGER NOT NULL)"
    )
}

val MIGRATION_13_14 = Migration(13, 14) { connection ->
    connection.execSQL(
        "ALTER TABLE another_db DROP COLUMN anotherTestColumn"
    )
}

val MIGRATION_14_15 = Migration(14, 15) { connection ->
    connection.execSQL(
        "ALTER TABLE another_db ADD COLUMN returnedColumn INTEGER NOT NULL"
    )
}

val MIGRATION_15_16 = Migration(15, 16) { connection ->
    connection.execSQL(
        "ALTER TABLE another_db DROP COLUMN returnedColumn"
    )
}

val MIGRATION_16_17 = Migration(16, 17) { connection ->
    connection.execSQL(
        "ALTER TABLE another_db ADD COLUMN testingAnother TEXT NOT NULL"
    )
}

val MIGRATION_17_18 = Migration(17, 18) { connection ->
    connection.execSQL(
        "ALTER TABLE another_db DROP COLUMN testingAnother"
    )

    connection.execSQL(
        "ALTER TABLE another_db ADD COLUMN testReplacementOne TEXT NOT NULL DEFAULT ''"

    )

    connection.execSQL(
        "ALTER TABLE another_db ADD COLUMN testReplacementTwo INTEGER NOT NULL DEFAULT 0"
    )
}

val MIGRATION_18_19 = Migration(18,19) {
    connection -> connection.execSQL(
        "DROP TABLE another_db"
    )
}






