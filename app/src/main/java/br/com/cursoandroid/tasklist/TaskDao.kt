package br.com.cursoandroid.tasklist

import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): MutableList<Task>
}