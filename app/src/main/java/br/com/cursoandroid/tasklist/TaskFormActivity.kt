package br.com.cursoandroid.tasklist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.cursoandroid.tasklist.databinding.ActivityTaskFormBinding

class TaskFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskFormBinding
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_form)

        binding.btAdd.setOnClickListener {
            // Verificar os campos e adicionar na lista
            val taskTitle = binding.etTaskTitle.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            val taskOwner = binding.etTaskOwner.text.toString()

            if(taskTitle.isNotBlank()) {
                val task = Task(taskTitle, taskDescription, taskOwner)
                taskList.add(task)
                Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show()
                binding.etTaskTitle.text?.clear()
                binding.etTaskOwner.text?.clear()
                binding.etTaskDescription.text?.clear()
                binding.etTaskTitle.requestFocus()
            } else {
                binding.etTaskTitle.error = "Campo Obrigatório"
            }
        }

        binding.btShowTasks.setOnClickListener {
            // Passar lista de task para a tela de listagem
            TaskListActivity.startActivity(this, taskList)
        }
    }
}