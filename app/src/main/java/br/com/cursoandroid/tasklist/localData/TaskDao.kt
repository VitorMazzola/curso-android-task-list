package br.com.cursoandroid.tasklist.localData

import androidx.room.*
import br.com.cursoandroid.tasklist.model.dataclass.Task

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