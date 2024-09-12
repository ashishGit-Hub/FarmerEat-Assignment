package com.ashishpatel.softwarelab.screens.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.activities.MainActivity
import com.ashishpatel.softwarelab.adapter.BusinessHoursRvAdapter
import com.ashishpatel.softwarelab.databinding.FragmentHoursBinding
import com.ashishpatel.softwarelab.screens.signup.models.BusinessHour
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.LoadingDialog
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HoursFragment : Fragment() {
    private var _binding: FragmentHoursBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BusinessHoursRvAdapter

    private val viewModel by activityViewModels<SignViewModel>()

    @Inject
    lateinit var loadingDialog: LoadingDialog

    private var hoursList: List<BusinessHour> = listOf(
        BusinessHour("8:00am - 10:00am", false, "monday"),
        BusinessHour("10:00am - 1:00pm", false, "monday"),
        BusinessHour("1:00pm - 4:00pm", false, "monday"),
        BusinessHour("4:00pm - 7:00pm", false, "monday"),
        BusinessHour("7:00pm - 10:00pm", false, "monday")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        adapter = BusinessHoursRvAdapter(::itemClick)
        return binding.root
    }

    private fun itemClick(businessHour: BusinessHour) {
        hoursList = hoursList.map {
            val data = if (businessHour.hour == it.hour && it.selected) {
                it.copy(selected = false)
            } else if (businessHour.hour == it.hour) {
                it.copy(selected = true)
            } else {
                it
            }
            data
        }
        viewModel.setHours(hoursList)
        adapter.submitList(hoursList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.businessHoursRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.businessHoursRv.setHasFixedSize(false)
        binding.businessHoursRv.adapter = adapter

        adapter.submitList(hoursList)

        binding.tuesday.background = if (viewModel.businessHours.tue.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.wednesday.background = if (viewModel.businessHours.wed.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.thursday.background = if (viewModel.businessHours.thu.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.friday.background = if (viewModel.businessHours.fri.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.saturday.background = if (viewModel.businessHours.sat.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.sunday.background = if (viewModel.businessHours.sun.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }

        binding.monday.setOnClickListener {
            setBackground()
            binding.monday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.monday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("monday", viewModel.businessHours.mon)
        }

        binding.tuesday.setOnClickListener {
            setBackground()
            binding.tuesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tuesday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("tuesday", viewModel.businessHours.tue)
        }

        binding.wednesday.setOnClickListener {
            setBackground()
            binding.wednesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.wednesday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("wednesday", viewModel.businessHours.wed)
        }

        binding.thursday.setOnClickListener {
            setBackground()
            binding.thursday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.thursday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("thursday", viewModel.businessHours.thu)
        }

        binding.friday.setOnClickListener {
            setBackground()
            binding.friday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.friday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("friday", viewModel.businessHours.fri)
        }

        binding.saturday.setOnClickListener {
            setBackground()
            binding.saturday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.saturday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("saturday", viewModel.businessHours.sat)
        }

        binding.sunday.setOnClickListener {
            setBackground()
            binding.sunday.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.sunday.background = ContextCompat.getDrawable(requireContext(),R.drawable.react_primary_bg)
            updateHours("sunday", viewModel.businessHours.sun)
        }

        binding.continueBtn.setOnClickListener {
            viewModel.register()
        }

        viewModel.authRegisterLiveData.observe(viewLifecycleOwner){
            loadingDialog.dismiss()
            when(it){
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    Intent(requireContext(),MainActivity::class.java).apply {
                        startActivity(this)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun setBackground(){
        binding.monday.background = if (viewModel.businessHours.mon.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.monday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))

        binding.tuesday.background = if (viewModel.businessHours.tue.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.wednesday.background = if (viewModel.businessHours.wed.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.thursday.background = if (viewModel.businessHours.thu.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.friday.background = if (viewModel.businessHours.fri.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.saturday.background = if (viewModel.businessHours.sat.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }
        binding.sunday.background = if (viewModel.businessHours.sun.isNotEmpty()){
            ContextCompat.getDrawable(requireContext(),R.drawable.input_text_bg)
        }else{
            ContextCompat.getDrawable(requireContext(),R.drawable.stroke_bg)
        }

        binding.monday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.tuesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.wednesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.thursday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.friday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.saturday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))
        binding.sunday.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_text_30))


    }

    private fun updateHours(day: String, hours: List<String>) {
        hoursList = hoursList.map {
            if (hours.any { item -> item == it.hour }) {
                it.copy(day = day, selected = true)
            } else {
                it.copy(day = day, selected = false)
            }
        }
        adapter.submitList(null)
        adapter.submitList(hoursList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}