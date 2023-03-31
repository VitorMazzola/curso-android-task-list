package br.com.cursoandroid.tasklist.remoteData

import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.model.dataclass.TaskDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {

    @GET("tasks")
    fun getTasks(): Call<List<Task>>

    @GET("tasks/detail")
    fun getTaskById(@Query("taskId") taskId: Int): Call<TaskDetail>
}