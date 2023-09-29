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
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.ToDoListViewModel
import com.example.todolist.ToDoListViewModelFactory
import com.example.todolist.data.ToDo
import com.example.todolist.databinding.FragmentEditBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EditFragment : Fragment() {
   private lateinit var binding: FragmentEditBinding
   private lateinit var toDo: ToDo
   private val navargs : EditFragmentArgs by navArgs()
    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).database.todoDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Edit Task"
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker
        viewModel.retrivetask(navargs.ToDoId).observe(this.viewLifecycleOwner){
            binding.Calender
            toDo = it
            binding.markAsDoneSwitch.isChecked = it.status
            viewModel.time.value = it.time
            viewModel.priority.value = it.priority
            viewModel.status.value = it.status
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(it.time))
            binding.DateCalender.text = formattedDate.toString()
            binding.taskEditText.setText(it.task)
            binding.prioritySlider.setValues(it.priority)
        }
        binding.prioritySlider.addOnChangeListener(RangeSlider.OnChangeListener { _, value, _ -> viewModel.priority.value = value  })
        binding.Calender.setOnClickListener {
            fragmentManager?.let { manager ->
                datePicker.show(manager, "DatePickerDialog")
                datePicker.addOnPositiveButtonClickListener {
                    viewModel.time.value = it
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(it))
                    binding.DateCalender.text = formattedDate.toString()
                }
            }
        }
        binding.markAsDoneSwitch.setOnCheckedChangeListener { _, b -> viewModel.status.value = b }
        binding.saveAction.setOnClickListener {
            if((binding.DateCalender.text.isNotEmpty()) && (!(binding.taskEditText.text.isNullOrEmpty())) ){
                viewModel.updateToDo(ToDo(navargs.ToDoId , viewModel.time.value!! ,
                    binding.DateCalender.text as String, binding.taskEditText.text.toString() ,viewModel.priority.value!! , viewModel.status.value!!))
                val action = EditFragmentDirections.actionEditFragmentToHomeFragment()
                findNavController().navigate(action)
            }

        }
        binding.deleteAction.setOnClickListener {
            showConfirmationDialog()
        }
    }
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.delete(toDo)
        val action = EditFragmentDirections.actionEditFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}