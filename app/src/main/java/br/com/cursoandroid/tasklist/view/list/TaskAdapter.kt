package br.com.cursoandroid.tasklist.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoandroid.tasklist.R
import br.com.cursoandroid.tasklist.databinding.ItemTaskListBinding
import br.com.cursoandroid.tasklist.model.dataclass.Task

class TaskAdapter(
    val context: Context,
    val taskList: MutableList<Task>,
    val listener: TaskListener,
    val isFromApi: Boolean = false
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

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
        holder.bind(taskList[position])
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
        notifyDataSetChanged()
    }

    fun updateTask() {
        notifyDataSetChanged()
    }

    // View Holder para controlar e manipular os itens da sua lista
    inner class TaskViewHolder(val binding: ItemTaskListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {

            binding.apply {
                taskTitle = task.title
                taskDescription = task.description
                taskOwner = task.owner
                isChecked = task.isChecked
                background = if(task.isChecked) ContextCompat.getColor(context, R.color.teal_700) else ContextCompat.getColor(context,
                    R.color.white
                )
                isEditEnabled = !isFromApi
            }

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                listener.onCheckboxClicked(task, isChecked)
            }

            binding.ivDelete.setOnClickListener {
                listener.onTaskDeleteClicked(task)
            }

            if(isFromApi) {
                binding.taskView.setOnClickListener {
                    listener.onTaskClicked(task.id)
                }
            }

            binding.ivEdit.setOnClickListener {
                listener.onEditTaskClicked(task.id)
            }
        }
    }
}