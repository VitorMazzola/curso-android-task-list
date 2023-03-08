package br.com.cursoandroid.tasklist

import android.content.DialogInterface
import android.os.Bundle
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

        configureListeners()
        configureRecyclerView()
    }

    // Método para setar os eventos dos componentes
    private fun configureListeners() {

        // Esse evento retorna o estado do componente (se foi ou nao digitado algo)
        binding.etTask.addTextChangedListener {
            binding.isButtonEnabled = it != null && it.isNotBlank()  //Checa se o editText tem ou nao texto e valor e cria as regras de exeção
            if(it == null || it.isBlank())
                binding.errorMessage = "Campo obrigatorio"
        }

        // Esse evento é para capturar o clique no componente
        binding.btAdd.setOnClickListener {
            // Capturar texto digitado mo editText
            val description = binding.etTask.text.toString()
            // Instancia o objeto
            val task = Task(description)
            // Atualiza o adapter com o objeto necessário da função addTask
            adapter?.addTask(task)
        }
    }

    // Método para instanciar seu adapter e configurar seu recyclerView (Lista)
    private fun configureRecyclerView() {
        adapter = TaskAdapter(this) // O adapter recebe um listener que no caso foi implementado pela Acitvity, por isso o uso do this
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
}