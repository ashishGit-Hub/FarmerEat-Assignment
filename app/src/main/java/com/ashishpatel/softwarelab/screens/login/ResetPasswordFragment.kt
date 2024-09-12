package com.ashishpatel.softwarelab.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentResetPasswordBinding
import com.ashishpatel.softwarelab.utils.Extension.getText
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.LoadingDialog
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")

        binding.submitBtn.setOnClickListener {
            val password = binding.passwordTil.getText("Password cannot be empty.")
            val cPassword = binding.passwordTil.getText("Confirm Password cannot be empty.")
            if(token != null && password != null && cPassword != null && password == cPassword) {
                viewModel.resetPassword(
                    mapOf(
                        "token" to token
                    )
                )
            }else{
                if (password != cPassword){
                    toast("Confirm password not match")
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
        }


        viewModel.resetPasswordLiveData.observe(viewLifecycleOwner){
            loadingDialog.dismiss()
            when(it){
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}