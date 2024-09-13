package com.ashishpatel.softwarelab.screens.signup

import android.app.Activity.RESULT_OK
import android.credentials.Credential
import android.credentials.CredentialOption
import android.credentials.GetCredentialRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.databinding.FragmentSignUpBinding
import com.ashishpatel.softwarelab.screens.signup.models.UserDetails
import com.ashishpatel.softwarelab.utils.Extension.getEmail
import com.ashishpatel.softwarelab.utils.Extension.getText
import com.ashishpatel.softwarelab.utils.Extension.toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SignViewModel>()

    private lateinit var auth: FirebaseAuth
    private var onTapClient: SignInClient? = null
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        Log.d("Registration", auth.currentUser?.displayName.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        onTapClient = Identity.getSignInClient(requireContext())
        return binding.root
    }


//    override fun onStart() {
//        super.onStart()
//        if (auth.currentUser != null) {
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fullNameTil.editText?.setText(viewModel.userDetails.fullName)
        binding.emailTil.editText?.setText(viewModel.userDetails.email)
        binding.phoneNumberTil.editText?.setText(viewModel.userDetails.phone)
        binding.passwordTil.editText?.setText(viewModel.userDetails.password)
        binding.reEnterPasswordTil.editText?.setText(viewModel.userDetails.password)

        binding.continueBtn.setOnClickListener {
            val input = validateInput()
            if (input != null) {
                viewModel.setUserDetails(input)
                findNavController().navigate(R.id.action_signUpFragment_to_formInfoFragment)
            }
        }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce("assignment")
        .build()



        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId("1005408726708-3cuj8tos6i30pre68b504ih0tfun3140.apps.googleusercontent.com")
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce(null)
                    .build()

            )
            .build()

        binding.googleBtn.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE){
//                val request: GetCredentialRequest = GetCredentialRequest.Builder()
//                    .addCredentialOption(googleIdOption).build()
            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    signingGoogle()
                }
            }

        }
    }

    private suspend fun signingGoogle() {
        try {
            val result = onTapClient?.beginSignIn(signInRequest)?.await()
            val intentSenderRequest = IntentSenderRequest.Builder(result!!.pendingIntent).build()
            activityResultLauncher.launch(intentSenderRequest)
        } catch (e: Error) {
            toast("Something went wrong! Please try after some time")
        }
    }

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                try {
                    val credential = onTapClient?.getSignInCredentialFromIntent(it.data)
                    val idToken = credential?.googleIdToken
                    when {
                        idToken != null -> {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
//                                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                    } else {
                                        Log.w(
                                            "Registration",
                                            "signInWithCredential:failure",
                                            task.exception
                                        )
                                    }
                                }
                        }

                        else -> {
                            toast("Something went wrong! Please try again")
                        }
                    }
                } catch (e: ApiException) {
                    toast("Something went wrong! Please try again")
                }
            }
        }


    private fun validateInput(): UserDetails? {
        val fullName = binding.fullNameTil.getText("Name cannot be empty.")
        val email = binding.emailTil.getEmail()
        val number = binding.phoneNumberTil.getText("Phone Number cannot be empty.")
        val password = binding.passwordTil.getText("Password cannot be empty.")
        val cPassword = binding.passwordTil.getText("Confirm Password cannot be empty.")

        if (cPassword != password) {
            binding.reEnterPasswordTil.isErrorEnabled = true
            binding.reEnterPasswordTil.error = "Confirm password not match"
            return null
        }

        return if (fullName != null && email != null && number != null && password != null) {
            UserDetails(
                fullName,
                email,
                number,
                password
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