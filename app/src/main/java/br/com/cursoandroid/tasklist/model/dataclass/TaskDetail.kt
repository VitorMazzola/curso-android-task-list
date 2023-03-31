package br.com.cursoandroid.tasklist.model.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TaskDetail(
    @SerializedName("fullDescription") val fullDescription: String,
    @SerializedName("ownerName") val owner: String,
    @SerializedName("isDone") val isDone: Boolean = false
) : Serializable