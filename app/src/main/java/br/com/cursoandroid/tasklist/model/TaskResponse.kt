package br.com.cursoandroid.tasklist.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TaskResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("owner") val owner: String
): Serializable