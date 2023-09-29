package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.HomeItemBinding

class DatesAdapter(private var OnItemClicked : (String) -> Unit ) : ListAdapter<String , DatesAdapter.DateViewHolder>(DiffCallback) {
    class DateViewHolder(private var binding: HomeItemBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(item : String){
                    binding.title.text = item
                }
            }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder(HomeItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val curritem = getItem(position)
        holder.itemView.setOnClickListener(){
            OnItemClicked(curritem)
        }
        holder.bind(curritem)
    }
}



