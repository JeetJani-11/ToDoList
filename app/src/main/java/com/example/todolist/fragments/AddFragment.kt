package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R

import com.example.todolist.ToDoListApplication
import com.example.todolist.ToDoListViewModel
import com.example.todolist.ToDoListViewModelFactory
import com.example.todolist.databinding.FragmentAddBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.RangeSlider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddFragment : Fragment() {
    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).database.todoDao()
        )
    }
    private lateinit var binding : FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Add Task"
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        binding.Calender.setOnClickListener {
            fragmentManager?.let { manager ->
                datePicker.show(manager, "DatePickerDialog")
                datePicker.addOnPositiveButtonClickListener {
                    viewModel.time.value  = it
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(it))
                    binding.DateCalender.text = formattedDate.toString()
                }
            }

        }
        binding.prioritySlider.addOnChangeListener(RangeSlider.OnChangeListener { _, value, _ -> viewModel.priority.value = value  })
        binding.saveAction.setOnClickListener {
            if((binding.DateCalender.text.isNotEmpty()) && (!(binding.taskEditText.text.isNullOrEmpty())) ){
                viewModel.addNewToDo(viewModel.time.value!! , binding.taskEditText.text.toString() , viewModel.priority.value!! , binding.DateCalender.text.toString() )
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }
    }
}