package br.com.cursoandroid.tasklist.view.list

import br.com.cursoandroid.tasklist.model.dataclass.Task

interface TaskListener {
    fun onTaskClicked(taskId: Int) {}
    fun onTaskDeleteClicked(task: Task) {}
    fun onCheckboxClicked(task: Task, isChecked: Boolean) {}
    fun onEditTaskClicked(taskId: Int) {}
}