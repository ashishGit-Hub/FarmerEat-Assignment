package com.ashishpatel.softwarelab.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentForgotPasswordBinding
import com.ashishpatel.softwarelab.utils.Extension.getText
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.LoadingDialog
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {


    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendCodeBtn.setOnClickListener {
            val number = binding.phoneNumberTil.getText("Phone Number cannot be empty.")
            if (number != null){
                viewModel.forgotPassword(number)
            }else{
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_verifyOTPFragment)
            }
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        viewModel.forgotPasswordLiveData.observe(viewLifecycleOwner){
            loadingDialog.dismiss()
            when(it){
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    val bundle = Bundle()
                    val number = binding.phoneNumberTil.editText?.text.toString()
                    bundle.putString("mobile",number)
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_verifyOTPFragment)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}