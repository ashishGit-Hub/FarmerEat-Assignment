package com.ashishpatel.softwarelab.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentFormInfoBinding
import com.ashishpatel.softwarelab.screens.signup.models.BusinessDetails
import com.ashishpatel.softwarelab.utils.Extension.getText
import com.ashishpatel.softwarelab.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormInfoFragment : Fragment() {
    private var _binding: FragmentFormInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SignViewModel>()


    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFormInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.businessNameTil.editText?.setText(viewModel.businessDetails.businessName)
        binding.informalNameTil.editText?.setText(viewModel.businessDetails.informalName)
        binding.addressNameTil.editText?.setText(viewModel.businessDetails.address)
        binding.cityTil.editText?.setText(viewModel.businessDetails.city)
        binding.stateTil.editText?.setText(viewModel.businessDetails.state)
        binding.zipCodeTil.editText?.setText(viewModel.businessDetails.zipCode)

        binding.continueBtn.setOnClickListener {
            val input = validateInput()
            if (input != null) {
                viewModel.setBusinessDetails(input)
                findNavController().navigate(R.id.action_formInfoFragment_to_verificationFragment)
            }
        }
    }

    private fun validateInput(): BusinessDetails? {

        val businessName = binding.businessNameTil.getText("Business Name cannot be empty.")
        val informalName = binding.informalNameTil.getText("Informal Name cannot be empty.")
        val address = binding.addressNameTil.getText("Address cannot be empty.")
        val city = binding.cityTil.getText("Address cannot be empty.")
        val state = binding.stateTil.getText("State cannot be empty.")
        val zipcode = binding.zipCodeTil.getText("Zipcode cannot be empty.")


        return if (businessName != null && informalName != null && address != null && state != null && zipcode != null && city != null) {
            BusinessDetails(
                businessName = businessName,
                informalName = informalName,
                address = address,
                city = city,
                state = state,
                zipCode = zipcode
            )
        } else {
            null
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}