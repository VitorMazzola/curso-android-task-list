package br.com.cursoandroid.tasklist

interface TaskListener {
    fun onTaskClicked(task: Task, position: Int) {}
    fun onTaskDeleteClicked(task: Task) {}
}