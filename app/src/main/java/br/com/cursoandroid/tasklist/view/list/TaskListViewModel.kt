package br.com.cursoandroid.tasklist.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.repository.IRepositoryLocal
import br.com.cursoandroid.tasklist.model.repository.IRepositoryRemote

class TaskListViewModel(
    val repositoryLocal: IRepositoryLocal,
    val repositoryRemote: IRepositoryRemote
): ViewModel() {

    val taskListRemote = MutableLiveData<List<Task>?>()
    val taskListRemoteError = MutableLiveData<Unit>()
    val taskListLocal = MutableLiveData<List<Task>?>()
    val isLoading = MutableLiveData(false)

    fun getTasks(isFromApi: Boolean) {
        if(isFromApi) {
            getTasksRemote()
        } else {
            taskListLocal.postValue(repositoryLocal.getAllTasks())
        }
    }

    private fun getTasksRemote() {
        repositoryRemote.getAllTasks(
            isLoading = {
               isLoading.postValue(it)
            },
            isSuccess = {
                taskListRemote.postValue(it)
            },
            isError = {
                taskListRemoteError.postValue(Unit)
            }
        )
    }

    fun deleteTask(task: Task) {
        repositoryLocal.deleteTask(task)
    }

}