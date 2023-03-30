package br.com.cursoandroid.tasklist.view.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.repository.IRepositoryLocal

class TaskFormViewModel(
    private val repositoryLocal: IRepositoryLocal
): ViewModel() {
    val requiredField = MutableLiveData<String>()

    fun addTask(title: String?, description: String?, owner: String?) {
        if(title != null && title.isNotEmpty()) {
            val task = Task(title = title, description = description, owner = owner)
            repositoryLocal.insertTask(task)
        } else {
           requiredField.postValue("Campo obrigatório")
        }
    }
}