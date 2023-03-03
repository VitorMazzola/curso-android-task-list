package br.com.cursoandroid.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    val listener: TaskListener
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = mutableListOf<Task>()

    // Inflar nosso layout e criar nosso ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false)
        return TaskViewHolder(view)
    }

    // Retorna o tamanho da lista
    override fun getItemCount(): Int {
        return taskList.size
    }

    // Chama seu ViewHolder passando os parametros definidos
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], position)
    }

    // Função para adicionar itens a lista e atualizar seu adapter
    fun addTask(task: Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

    // View Holder para controlar e manipular os itens da sua lista
    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, itemPosition: Int) {
            val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
            tvDescription.text = task.description

            itemView.setOnClickListener {
                listener.onTaskClicked(task, itemPosition)
            }
        }
    }
}