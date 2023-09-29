package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.ToDo
import com.example.todolist.databinding.HomeItemBinding
import com.example.todolist.databinding.TaskItemBinding

class TasksAdapter(private var OnItemClicked : (Int) -> Unit ) : ListAdapter<ToDo  , TasksAdapter.TaskViewHolder>(DiffCallback) {
    class TaskViewHolder(private var binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(item : ToDo){
            binding.task.text = item.task
            binding.Priority.text = item.priority.toString()
            if(item.status){
                binding.task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TasksAdapter.TaskViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder , position: Int) {
        val curritem = getItem(position)
        holder.itemView.setOnClickListener {
            OnItemClicked(curritem.id)
        }
        holder.bind(curritem)
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.task == newItem.task
            }
        }
    }

}

