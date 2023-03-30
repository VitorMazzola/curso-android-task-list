package br.com.cursoandroid.tasklist.remoteData

import br.com.cursoandroid.tasklist.model.dataclass.Task
import retrofit2.Call
import retrofit2.http.GET

interface IApi {

    @GET("tasks")
    fun getTasks(): Call<List<Task>>
}