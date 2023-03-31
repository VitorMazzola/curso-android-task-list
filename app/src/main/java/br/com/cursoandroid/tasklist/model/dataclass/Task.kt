package br.com.cursoandroid.tasklist.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String? = "",
    var description: String? = "",
    val owner: String? = "",
    var isChecked: Boolean = false
): Serializable
