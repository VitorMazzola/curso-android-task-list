package br.com.cursoandroid.tasklist.remoteData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    fun getService() = Retrofit.Builder()
        .baseUrl("https://demo8317484.mockable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}