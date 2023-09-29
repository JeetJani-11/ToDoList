package com.example.todolist

import android.app.Application
import com.example.todolist.data.ToDoRoomDatabase

class ToDoListApplication : Application()
{
    val database: ToDoRoomDatabase by lazy { ToDoRoomDatabase.getDatabase(this) }
}