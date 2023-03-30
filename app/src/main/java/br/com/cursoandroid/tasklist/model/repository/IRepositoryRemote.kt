package br.com.cursoandroid.tasklist.model.repository

import br.com.cursoandroid.tasklist.model.dataclass.Task

interface IRepositoryRemote {
    fun getAllTasks(isLoading: (Boolean) -> Unit, isSuccess: ((List<Task>?)) -> Unit, isError: () -> Unit)
}