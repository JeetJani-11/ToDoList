package com.example.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,

    @ColumnInfo(name = "Time")
    val time : Long ,

    @ColumnInfo(name = "Date")
    val date : String ,

    @ColumnInfo(name = "Task")
    val task : String ,

    @ColumnInfo(name = "Priority")
    val priority : Float ,

    @ColumnInfo(name = "Status")
    var status : Boolean
)
