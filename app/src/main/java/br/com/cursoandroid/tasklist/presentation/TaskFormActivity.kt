package br.com.cursoandroid.tasklist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.cursoandroid.tasklist.R
import br.com.cursoandroid.tasklist.model.Task
import br.com.cursoandroid.tasklist.localData.TaskDatabase
import br.com.cursoandroid.tasklist.databinding.ActivityTaskFormBinding

class TaskFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskFormBinding
    private var taskDatabase: TaskDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_form)

        taskDatabase = TaskDatabase.getDatabaseInstance(this)
        configureClicks()
    }

    private fun configureClicks() {
        binding.btAdd.setOnClickListener {
            // Verificar os campos e adicionar na lista
            val taskTitle = binding.etTaskTitle.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            val taskOwner = binding.etTaskOwner.text.toString()

            if(taskTitle.isNotBlank()) {
                val task = Task(title = taskTitle, description = taskDescription, owner = taskOwner)
                addTask(task)
            } else {
                binding.etTaskTitle.error = "Campo Obrigatório"
            }
        }

        binding.btShowTasks.setOnClickListener {
            // Passar lista de task para a tela de listagem
            TaskListActivity.startActivity(this)
        }

        binding.btShowTasksRemote.setOnClickListener {
            TaskListActivity.startActivity(this, true)
        }

    }

    private fun addTask(task: Task) {
        taskDatabase?.taskDao()?.insert(task)
        Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show()
        binding.etTaskTitle.text?.clear()
        binding.etTaskOwner.text?.clear()
        binding.etTaskDescription.text?.clear()
        binding.etTaskTitle.requestFocus()
    }

}