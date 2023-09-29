package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.ToDoListViewModel
import com.example.todolist.ToDoListViewModelFactory
import com.example.todolist.adapter.DatesAdapter
import com.example.todolist.databinding.FragmentHomeBinding
import javax.net.ssl.SSLSessionBindingEvent


class HomeFragment : Fragment() {
   private lateinit var binding : FragmentHomeBinding
    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).database.todoDao()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "ToDo"
        binding.AddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        val adapter = DatesAdapter{
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            this.findNavController().navigate(action)
        }
        binding.RecyclerView.adapter = adapter
        viewModel.Dates.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
    }
}
