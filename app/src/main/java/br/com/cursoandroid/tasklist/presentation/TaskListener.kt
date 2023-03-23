package br.com.cursoandroid.tasklist.presentation

import br.com.cursoandroid.tasklist.model.Task

interface TaskListener {
    fun onTaskClicked(task: Task, position: Int) {}
    fun onTaskDeleteClicked(task: Task) {}
    fun onCheckboxClicked(task: Task, isChecked: Boolean) {}
}