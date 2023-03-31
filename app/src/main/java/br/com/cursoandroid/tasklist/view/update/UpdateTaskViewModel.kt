package br.com.cursoandroid.tasklist.view.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.repository.IRepositoryLocal


class UpdateTaskViewModel(
    private val repositoryLocal: IRepositoryLocal
): ViewModel() {

    val taskObservable = MutableLiveData<Task>()
    val updateFailed = MutableLiveData<String>()
    val updateSuccess = MutableLiveData<Unit>()

    fun getTaskById(taskId: Int) {
        val task = repositoryLocal.getTaskById(taskId)
        task?.let { taskObservable.postValue(it) }
    }

    fun updateTask(taskId: Int, newTitle: String?, newDescription: String?) {
        val task = repositoryLocal.getTaskById(taskId)

        if(task != null && (task.title != newTitle || task.description != newDescription)) {
            task.title = newTitle
            task.description = newDescription
            repositoryLocal.updateTask(task)
            updateSuccess.postValue(Unit)
        } else {
            updateFailed.postValue("Task sem dados alterados")
        }
    }

}