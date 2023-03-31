package br.com.cursoandroid.tasklist.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.dataclass.TaskDetail
import br.com.cursoandroid.tasklist.model.repository.IRepositoryRemote

class TaskDetailViewModel(
    private val repositoryRemote: IRepositoryRemote
): ViewModel() {

    val isLoading = MutableLiveData(false)
    val taskResponseSuccess = MutableLiveData<TaskDetail>()
    val taskResponseError = MutableLiveData<Unit>()

    fun getTaskById(taskId: Int) {
        repositoryRemote.getTaskById(taskId,
            isSuccess = { taskDetail ->
                taskDetail?.let {
                    taskResponseSuccess.postValue(it)
                } ?: run {
                    taskResponseError.postValue(Unit)
                }
            },
            isError = {
                taskResponseError.postValue(Unit)
            },
            isLoading = {
                isLoading.postValue(it)
            }
        )
    }
}