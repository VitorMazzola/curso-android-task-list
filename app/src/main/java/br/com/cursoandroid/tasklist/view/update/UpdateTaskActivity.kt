package br.com.cursoandroid.tasklist.view.update

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cursoandroid.tasklist.R
import br.com.cursoandroid.tasklist.databinding.ActivityTaskUpdateBinding
import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.model.repository.RepositoryLocal
import br.com.cursoandroid.tasklist.model.repository.RepositoryRemote
import br.com.cursoandroid.tasklist.remoteData.ApiService
import br.com.cursoandroid.tasklist.remoteData.IApi
import br.com.cursoandroid.tasklist.view.list.TaskListViewModel

class UpdateTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskUpdateBinding

    private lateinit var viewModel: UpdateTaskViewModel

    private val taskId: Int? by lazy { intent.extras?.getInt(TASK_ID_ARG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_update)

        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UpdateTaskViewModel(
                    repositoryLocal = RepositoryLocal(TaskDatabase.getDatabaseInstance(this@UpdateTaskActivity)),
                ) as T
            }
        })[UpdateTaskViewModel::class.java]

        taskId?.let { id ->
            viewModel.getTaskById(id)
        } ?: run {
            finish()
        }

        configureObservable()
        configureClickListeners()
    }

    private fun configureObservable() {
        viewModel.taskObservable.observe(this) { task ->
            binding.apply {
                title = task.title
                description = task.description
            }
        }

        viewModel.updateSuccess.observe(this) {
            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        viewModel.updateFailed.observe(this) { failedMsg ->
            Toast.makeText(this, failedMsg, Toast.LENGTH_LONG).show()
        }

        binding.etTaskTitle.addTextChangedListener {
            binding.title = it.toString()
            updateButton()
        }

        binding.etTaskDescription.addTextChangedListener {
            binding.description = it.toString()
            updateButton()
        }
    }

    private fun configureClickListeners() {
        binding.btUpdate.apply {
            setOnClickListener {
                taskId?.let {
                    viewModel.updateTask(it, binding.title, binding.description)
                }
            }
        }
    }

    private fun updateButton() {
        binding.apply {
            isButtonEnabled = title?.isNotBlank() == true && description?.isNotBlank() == true
        }
    }

    companion object {
        const val TASK_ID_ARG = "taskIdArgs"
        fun startActivity(context: Context, taskId: Int): Intent {
            val intent = Intent(context, UpdateTaskActivity::class.java)
            intent.putExtra(TASK_ID_ARG, taskId)
            return intent
        }
    }
}