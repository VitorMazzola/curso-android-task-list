package br.com.cursoandroid.tasklist.model.repository

import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.model.dataclass.Task

class RepositoryLocal(private val taskDatabase: TaskDatabase? = null): IRepositoryLocal {

    override fun insertTask(task: Task) {
        taskDatabase?.taskDao()?.insert(task)
    }

    override fun getAllTasks(): List<Task>? {
        return taskDatabase?.taskDao()?.getAllTasks()
    }

    override fun deleteTask(task: Task) {
        taskDatabase?.taskDao()?.delete(task)
    }
}