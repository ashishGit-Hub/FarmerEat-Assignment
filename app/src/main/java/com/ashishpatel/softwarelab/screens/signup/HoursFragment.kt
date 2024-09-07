package com.ashishpatel.softwarelab.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashishpatel.softwarelab.databinding.FragmentHoursBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoursFragment : Fragment() {
    private var _binding: FragmentHoursBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.businessHoursRv.layoutManager = GridLayoutManager(requireContext(),2)
        binding.businessHoursRv.setHasFixedSize(false)
        binding.businessHoursRv.adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}