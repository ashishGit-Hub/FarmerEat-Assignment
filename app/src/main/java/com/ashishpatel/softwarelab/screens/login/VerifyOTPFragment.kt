package com.ashishpatel.softwarelab.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentVerifyOTPBinding
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.ashishpatel.softwarelab.utils.LoadingDialog
import com.ashishpatel.softwarelab.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VerifyOTPFragment : Fragment() {

    private var _binding: FragmentVerifyOTPBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mobile = arguments?.getString("mobile")

        binding.one.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                binding.two.requestFocus()
            }
        }
        binding.two.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                binding.three.requestFocus()
            }
        }
        binding.three.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                binding.four.requestFocus()
            }

        }
        binding.four.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                binding.five.requestFocus()
            }
        }

        binding.submitBtn.setOnClickListener {
            val one = binding.one.text.toString().trim()
            val two = binding.two.text.toString().trim()
            val three = binding.three.text.toString().trim()
            val four = binding.four.text.toString().trim()
            val five = binding.five.text.toString().trim()

            if (one.isNotEmpty() && two.isNotEmpty() && three.isNotEmpty() && four.isNotEmpty() && five.isNotEmpty()) {
                val otp = one+two+three+four+five
                viewModel.verifyOTP(otp)
            }
        }

        binding.resentOtpBtn.setOnClickListener {
            if (mobile != null) {
                viewModel.forgotPassword(mobile)
            }else{
                toast("something went wrong! Please re-enter phone number")
            }
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_verifyOTPFragment_to_loginFragment)
        }


        viewModel.verifyOTPLiveData.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
            when (it) {
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    val bundle = Bundle()
                    bundle.putString("token", it.data)
                    findNavController().navigate(
                        R.id.action_verifyOTPFragment_to_resetPasswordFragment,
                        bundle
                    )
                }
            }
        }

        viewModel.forgotPasswordLiveData.observe(viewLifecycleOwner){
            loadingDialog.dismiss()
            when(it){
                is NetworkResult.Error -> toast(it.message)
                is NetworkResult.Loading -> loadingDialog.startLoading()
                is NetworkResult.Success -> {
                    toast("OTP send successfully")
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}