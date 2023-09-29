package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.ToDoListViewModel
import com.example.todolist.ToDoListViewModelFactory
import com.example.todolist.adapter.TasksAdapter
import com.example.todolist.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val navigationArgs: DetailFragmentArgs by navArgs()
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
        binding = FragmentDetailBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retriveTasks(navigationArgs.Date)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = navigationArgs.Date
        val adapter = TasksAdapter{
            val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(it)
            findNavController().navigate(action)
        }
        binding.RecyclerView.adapter = adapter
        viewModel.tasks.observe(this.viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
    }
}