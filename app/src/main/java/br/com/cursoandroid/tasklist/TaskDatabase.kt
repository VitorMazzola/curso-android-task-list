package br.com.cursoandroid.tasklist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabaseInstance(context: Context): TaskDatabase? {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    TaskDatabase::class.java,
                    "task-database"
                )
                .allowMainThreadQueries()
                .build()
            }

            return INSTANCE
        }
    }
}