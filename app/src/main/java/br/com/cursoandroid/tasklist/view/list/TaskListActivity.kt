package br.com.cursoandroid.tasklist.view.list

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cursoandroid.tasklist.*
import br.com.cursoandroid.tasklist.databinding.ActivityTaskListBinding
import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.repository.RepositoryLocal
import br.com.cursoandroid.tasklist.model.repository.RepositoryRemote
import br.com.cursoandroid.tasklist.remoteData.ApiService
import br.com.cursoandroid.tasklist.remoteData.IApi
import br.com.cursoandroid.tasklist.view.detail.TaskDetailActivity
import br.com.cursoandroid.tasklist.view.update.UpdateTaskActivity

class TaskListActivity: AppCompatActivity(), TaskListener {

    private var adapter: TaskAdapter? = null

    // Declarar binding da view
    private lateinit var binding: ActivityTaskListBinding

    private lateinit var viewModel: TaskListViewModel

    private var taskDatabase: TaskDatabase? = null

    private val isFromApi: Boolean by lazy { intent.extras?.getBoolean(IS_FROM_API) ?: false }

    // Metodo do ciclo de vida da activity (onde é criado e setado o layout definido)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_list)

        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskListViewModel(
                    repositoryLocal =  RepositoryLocal(TaskDatabase.getDatabaseInstance(this@TaskListActivity)),
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java))
                ) as T
            }
        })[TaskListViewModel::class.java]

        viewModel.getTasks(isFromApi)

        configureObservables()
    }

    private fun configureObservables() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.loading.isVisible = isLoading
        }

        viewModel.taskListLocal.observe(this) { tasks ->
            tasks?.let {
                configureRecyclerView(tasks.toMutableList())
            }
        }

        viewModel.taskListRemote.observe(this) { taskRemote ->
            taskRemote?.let {
                configureRecyclerView(it.toMutableList())
            }
        }

        viewModel.taskListRemoteError.observe(this) {
            Toast.makeText(this, "Erro de servidor", Toast.LENGTH_LONG).show()
        }
    }

    // Método para instanciar seu adapter e configurar seu recyclerView (Lista)
    private fun configureRecyclerView(taskList: MutableList<Task>) {
        adapter = TaskAdapter(this, taskList,this, isFromApi) // O adapter recebe um listener que no caso foi implementado pela Acitvity, por isso o uso do this
        binding.recyclerTasks.adapter = adapter
        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
    }

    // Implementação do método da interface TaskListener implementada
    override fun onTaskClicked(taskId: Int) {
        TaskDetailActivity.startActivity(this, taskId)
    }

    override fun onTaskDeleteClicked(task: Task) {
        showDialog(task)
    }

    override fun onEditTaskClicked(taskId: Int) {
        val intent = UpdateTaskActivity.startActivity(this, taskId)
        updateLauncher.launch(intent)
    }

    private val updateLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            viewModel.getTasks(false)
        }
    }

    override fun onCheckboxClicked(task: Task, isChecked: Boolean) {
        task.isChecked = isChecked
        viewModel.deleteTask(task)
        adapter?.updateTask()
    }

    // Método para criar um Alert Dialog
    private fun showDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(task.description)
            .setMessage("Deseja remover este item?")
            .setPositiveButton("Sim", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    adapter?.deleteTask(task)
                    taskDatabase?.taskDao()?.delete(task)
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        const val IS_FROM_API = "isFromApiArgs"
        fun startActivity(context: Context, isFromApi: Boolean = false) {
            val intent = Intent(context, TaskListActivity::class.java)
            intent.putExtra(IS_FROM_API, isFromApi)
            context.startActivity(intent)
        }
    }
}