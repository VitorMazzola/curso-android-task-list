package br.com.cursoandroid.tasklist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class TaskListActivity: AppCompatActivity() {

    private var etTaskDescription: TextInputEditText? = null
    private var btAdd: Button? = null
    private var adapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        bindingViews()
        configureClicks()
        configureRecyclerView()
    }

    private fun bindingViews() {
        etTaskDescription = findViewById(R.id.etTask)
        btAdd = findViewById(R.id.btAdd)
    }

    private fun configureClicks() {
        btAdd?.setOnClickListener {
            // Capturar texto do editText e criar um objeto Task
            val description = etTaskDescription?.text.toString()
            val task = Task(description)
            adapter?.addTask(task)
        }
    }

    private fun configureRecyclerView() {
        val recyclerTasks = findViewById<RecyclerView>(R.id.recyclerTasks)
        adapter = TaskAdapter()
        recyclerTasks.adapter = adapter
        recyclerTasks.layoutManager = LinearLayoutManager(this)
    }
}