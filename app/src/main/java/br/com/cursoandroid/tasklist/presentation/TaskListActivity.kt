package br.com.cursoandroid.tasklist.presentation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cursoandroid.tasklist.*
import br.com.cursoandroid.tasklist.databinding.ActivityTaskListBinding
import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.model.Task
import br.com.cursoandroid.tasklist.remoteData.ApiService
import br.com.cursoandroid.tasklist.remoteData.IApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskListActivity: AppCompatActivity(), TaskListener {

    private var adapter: TaskAdapter? = null

    // Declarar binding da view
    private lateinit var binding: ActivityTaskListBinding

    private var taskDatabase: TaskDatabase? = null

    // Metodo do ciclo de vida da activity (onde é criado e setado o layout definido)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_list)

        val isFromApi = intent.extras?.getBoolean(IS_FROM_API) ?: false

        if(isFromApi) {
            getTasksFromApi()
        } else {
            getTasksFromLocalDatabase()
        }

    }

    private fun getTasksFromApi() {
        val apiService = ApiService.getService().create(IApi::class.java)
        val callGetTasks = apiService.getTasks()

        binding.isLoading = View.VISIBLE
        callGetTasks.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                when(response.code()) {
                    in 200..202 -> {
                        val task = response.body()
                        task?.let {
                            configureRecyclerView(it.toMutableList())
                        }
                    }
                    204 -> {
                        Toast.makeText(this@TaskListActivity, "Lista Vazia", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@TaskListActivity, "Falha no servidor", Toast.LENGTH_LONG).show()
                    }
                }

                binding.isLoading = View.GONE
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Toast.makeText(this@TaskListActivity, "Falha no servidor", Toast.LENGTH_LONG).show()
                binding.isLoading = View.GONE
            }

        })
    }

    private fun getTasksFromLocalDatabase() {
        taskDatabase = TaskDatabase.getDatabaseInstance(this)
        val taskList = taskDatabase?.taskDao()?.getAllTasks()
        taskList?.let {
            configureRecyclerView(it)
        }
    }

    // Método para instanciar seu adapter e configurar seu recyclerView (Lista)
    private fun configureRecyclerView(taskList: MutableList<Task>) {
        adapter = TaskAdapter(this, taskList,this) // O adapter recebe um listener que no caso foi implementado pela Acitvity, por isso o uso do this
        binding.recyclerTasks.adapter = adapter
        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
    }

    // Implementação do método da interface TaskListener implementada
    override fun onTaskClicked(task: Task, position: Int) {
        showDialog(task)
    }

    override fun onTaskDeleteClicked(task: Task) {
        showDialog(task)
    }

    override fun onCheckboxClicked(task: Task, isChecked: Boolean) {
        task.isChecked = isChecked
        taskDatabase?.taskDao()?.update(task)
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