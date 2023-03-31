package br.com.cursoandroid.tasklist.model.repository

import br.com.cursoandroid.tasklist.model.dataclass.Task

interface IRepositoryLocal {
    fun insertTask(task: Task) {}

    fun getAllTasks(): List<Task>?

    fun deleteTask(task: Task) {}

    fun getTaskById(taskId: Int) : Task?

    fun updateTask(task: Task) {}
}