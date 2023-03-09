package br.com.cursoandroid.tasklist

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cursoandroid.tasklist.databinding.ActivityTaskListBinding

class TaskListActivity: AppCompatActivity(), TaskListener {

    private var adapter: TaskAdapter? = null

    // Declarar binding da view
    private lateinit var binding: ActivityTaskListBinding

    // Metodo do ciclo de vida da activity (onde é criado e setado o layout definido)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_list)

        val taskList = intent.extras?.getSerializable(LIST_ARGS) as? ArrayList<Task>
        taskList?.let {
            configureRecyclerView(it)
        }
    }

    // Método para instanciar seu adapter e configurar seu recyclerView (Lista)
    private fun configureRecyclerView(taskList: MutableList<Task>) {
        adapter = TaskAdapter(taskList,this) // O adapter recebe um listener que no caso foi implementado pela Acitvity, por isso o uso do this
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

    // Método para criar um Alert Dialog
    private fun showDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(task.description)
            .setMessage("Deseja remover este item?")
            .setPositiveButton("Sim", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    adapter?.deleteTask(task)
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        const val LIST_ARGS = "listArgs"
        fun startActivity(context: Context, taskList: MutableList<Task>) {
            val intent = Intent(context, TaskListActivity::class.java)
            intent.putExtras(Bundle().apply {
                putSerializable(LIST_ARGS, ArrayList(taskList))
            })
            context.startActivity(intent)
        }
    }
}