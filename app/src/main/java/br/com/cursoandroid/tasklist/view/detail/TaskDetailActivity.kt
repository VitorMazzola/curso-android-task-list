package br.com.cursoandroid.tasklist.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cursoandroid.tasklist.R
import br.com.cursoandroid.tasklist.databinding.ActivityTaskDetailBinding
import br.com.cursoandroid.tasklist.model.repository.RepositoryRemote
import br.com.cursoandroid.tasklist.remoteData.ApiService
import br.com.cursoandroid.tasklist.remoteData.IApi

class TaskDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding

    private lateinit var viewModel: TaskDetailViewModel

    private val taskId: Int? by lazy { intent.extras?.getInt(TASK_ID_ARG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail)

        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskDetailViewModel(
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java))
                ) as T
            }
        })[TaskDetailViewModel::class.java]

        taskId?.let {
            viewModel.getTaskById(it)
        } ?: run {
            finish()
        }

        configureObservables()
    }

    private fun configureObservables() {
        viewModel.isLoading.observe(this) {
            binding.isLoading = it
        }

        viewModel.taskResponseSuccess.observe(this) {
            binding.apply {
                fullDescription = it.fullDescription
                owner = it.owner
                isDone = it.isDone
            }
        }

        viewModel.taskResponseError.observe(this) {
            Toast.makeText(this, "Erro ao buscar tarefa", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TASK_ID_ARG = "taskIdArgs"
        fun startActivity(context: Context, taskId: Int) {
            val intent = Intent(context, TaskDetailActivity::class.java)
            intent.putExtra(TASK_ID_ARG, taskId)
            context.startActivity(intent)
        }
    }
}