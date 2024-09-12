package com.ashishpatel.softwarelab.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentVerificationBinding
import com.ashishpatel.softwarelab.utils.Extension.gone
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.Extension.visible
import com.ashishpatel.softwarelab.utils.UtilsClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SignViewModel>()

    @Inject
    lateinit var utilsClass : UtilsClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectDocument.setOnClickListener {
            pickPdf.launch("application/pdf")
        }

        binding.continueBtn.setOnClickListener {
            if (viewModel.documentUri != null) {
                findNavController().navigate(R.id.action_verificationFragment_to_hoursFragment)
            } else {
                toast("Please select verification document")
            }
        }

        binding.removeProofIv.setOnClickListener {
            viewModel.documentUri = null
            binding.selectedFileTxt.text = ""
            binding.attachmentProofLayout.gone()
        }
    }

    private val pickPdf = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            viewModel.documentUri = it
            binding.selectedFileTxt.text = utilsClass.getFileName(it)
            binding.attachmentProofLayout.visible()
        } else {
            viewModel.documentUri?.let { it1 ->
                binding.selectedFileTxt.text = utilsClass.getFileName(it1)
            } ?: {
                binding.attachmentProofLayout.gone()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}