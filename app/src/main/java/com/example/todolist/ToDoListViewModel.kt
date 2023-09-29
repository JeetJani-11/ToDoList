package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDo
import com.example.todolist.data.ToDoDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class ToDoListViewModel(private val toDoDao: ToDoDao) : ViewModel() {
    val priority: MutableLiveData<Float> = MutableLiveData(0.toFloat())
    val time: MutableLiveData<Long> = MutableLiveData(0)
    val Dates: LiveData<List<String>> = toDoDao.getDates().asLiveData()
    var tasks: LiveData<List<ToDo>> = MutableLiveData(listOf())
    var status : MutableLiveData<Boolean> = MutableLiveData(false)
    fun retriveTasks(date: String) {
        viewModelScope.launch {
            tasks = toDoDao.getTasks(date).asLiveData()

        }
    }
    fun retrivetask(id : Int) : LiveData<ToDo>{
       return toDoDao.getTask(id).asLiveData()
    }
    fun addNewToDo(Time: Long, Task: String, Priority: Float, Date: String) {
            viewModelScope.launch {
                toDoDao.insert(ToDo(0, Time, Date, Task, Priority , false))

            }
        }
    fun updateToDo(todo : ToDo){
        viewModelScope.launch {
            toDoDao.update(todo)
        }
    }

    fun delete(todo : ToDo){
        viewModelScope.launch {
            toDoDao.delete(todo)
        }
    }

    }

    class ToDoListViewModelFactory(private val toDoDao: ToDoDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ToDoListViewModel(toDoDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
