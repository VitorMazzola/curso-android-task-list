package br.com.cursoandroid.tasklist.model.repository

import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.dataclass.TaskDetail

interface IRepositoryRemote {
    fun getAllTasks(isLoading: (Boolean) -> Unit, isSuccess: ((List<Task>?)) -> Unit, isError: () -> Unit)
    fun getTaskById(taskId: Int, isLoading: (Boolean) -> Unit, isSuccess: (TaskDetail?) -> Unit, isError: () -> Unit)
}