package br.com.cursoandroid.tasklist.view.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cursoandroid.tasklist.R
import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.databinding.ActivityTaskFormBinding
import br.com.cursoandroid.tasklist.view.list.TaskListActivity
import br.com.cursoandroid.tasklist.model.repository.RepositoryLocal

class TaskFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskFormBinding
    private lateinit var viewModel: TaskFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_form)

        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskFormViewModel(
                    RepositoryLocal(TaskDatabase.getDatabaseInstance(this@TaskFormActivity))
                ) as T
            }
        })[TaskFormViewModel::class.java]

        configureClicks()
        configureObservables()
    }

    private fun configureObservables() {
        viewModel.requiredField.observe(this) {
            binding.error = it
        }
    }

    private fun configureClicks() {
        binding.btAdd.setOnClickListener {
            // Verificar os campos e adicionar na lista
            val taskTitle = binding.etTaskTitle.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            val taskOwner = binding.etTaskOwner.text.toString()
            viewModel.addTask(taskTitle, taskDescription, taskOwner)
            clearField()
        }

        binding.btShowTasks.setOnClickListener {
            // Passar lista de task para a tela de listagem
            TaskListActivity.startActivity(this)
        }

        binding.btShowTasksRemote.setOnClickListener {
            TaskListActivity.startActivity(this, true)
        }

    }

    private fun clearField() {
        binding.etTaskTitle.text?.clear()
        binding.etTaskOwner.text?.clear()
        binding.etTaskDescription.text?.clear()
        binding.etTaskTitle.requestFocus()
    }

}