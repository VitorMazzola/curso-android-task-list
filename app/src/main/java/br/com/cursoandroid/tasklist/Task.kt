package br.com.cursoandroid.tasklist

import java.io.Serializable

data class Task (
    val title: String,
    val description: String
): Serializable
