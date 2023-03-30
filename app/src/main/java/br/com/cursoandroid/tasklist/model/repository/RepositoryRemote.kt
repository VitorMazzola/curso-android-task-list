package br.com.cursoandroid.tasklist.model.repository

import br.com.cursoandroid.tasklist.model.dataclass.Task
import br.com.cursoandroid.tasklist.remoteData.IApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryRemote(val api: IApi) : IRepositoryRemote {
    override fun getAllTasks(isLoading: (Boolean) -> Unit, isSuccess: (List<Task>?) -> Unit, isError: () -> Unit) {
        isLoading.invoke(true)
        val call = api.getTasks()
        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                when (response.code()) {
                    in 200..202 -> {
                        isSuccess.invoke(response.body())
                    }
                    204 -> {
                        isSuccess.invoke(listOf())
                    }
                    else -> {
                        isError.invoke()
                    }
                }
                isLoading.invoke(false)
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                isError.invoke()
                isLoading.invoke(false)
            }
        })
    }

}