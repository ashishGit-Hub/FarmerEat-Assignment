package com.ashishpatel.softwarelab.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.activities.MainActivity
import com.ashishpatel.softwarelab.databinding.FragmentLoginBinding
import com.ashishpatel.softwarelab.utils.Extension.getEmail
import com.ashishpatel.softwarelab.utils.Extension.getText
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.LoadingDialog
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<LoginViewModel>()


    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forgotPassBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.createAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

//        {
//            "email": "johndoe@mail.com",
//            "password": "12345678",
//            "role": "farmer",
//            "device_token": "0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx",
//            "type": "email",
//            "social_id": "0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx"
//        }

        binding.loginBtn.setOnClickListener {
            val input = validateInput()
            if (input != null) {
                viewModel.login(
                    email = input.first,
                    password = input.second,
                    role = "farmer",
                    type = "email",
                    socialId = "",
                )
            }
        }

        viewModel.authLoginLiveData.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
            when (it) {
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    toast("Login Successfully")
                    Intent(requireContext(), MainActivity::class.java).apply {
                        startActivity(this)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun validateInput(): Pair<String, String>? {
        val email = binding.emailTil.getEmail()
        val password = binding.passwordTil.getText(getString(R.string.email_cannot_be_empty))

        if (email != null && password != null) {
            return Pair(email, password)
        }

        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}