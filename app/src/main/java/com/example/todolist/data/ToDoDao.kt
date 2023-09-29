package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDo: ToDo)

    @Query("Select * from todo Where Date = :date ORDER BY Priority DESC")
    fun getTasks(date : String) : Flow<List<ToDo>>

    @Query("Select * from ToDo Where id = :id ")
    fun getTask(id : Int) : Flow<ToDo>

    @Delete
    suspend fun delete (toDo: ToDo)

    @Update
    suspend fun update(toDo : ToDo)

    @Query("Select  DISTINCT Date from todo ORDER BY Date Asc")
    fun getDates() : Flow<List<String>>
}