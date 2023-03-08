package br.com.cursoandroid.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoandroid.tasklist.databinding.ItemTaskListBinding

class TaskAdapter(
    val listener: TaskListener
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = mutableListOf<Task>()

    // Inflar nosso layout e criar nosso ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding: ItemTaskListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_task_list,
            parent,
            false
        )
        return TaskViewHolder(binding)
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

    fun deleteTask(task: Task) {
        taskList.remove(task)
        notifyDataSetChanged()
    }

    // View Holder para controlar e manipular os itens da sua lista
    inner class TaskViewHolder(val binding: ItemTaskListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, itemPosition: Int) {

            binding.apply {
                taskDescription = task.description
            }

            binding.ivDelete.setOnClickListener {
                listener.onTaskDeleteClicked(task)
            }
        }
    }
}