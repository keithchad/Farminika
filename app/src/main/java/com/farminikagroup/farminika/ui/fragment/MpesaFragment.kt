package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.callback.DarajaResult
import com.androidstudy.daraja.util.Environment
import com.farminikagroup.farminika.utils.AppUtils
import com.farminikagroup.farminika.databinding.FragmentMpesaBinding
import com.farminikagroup.farminika.utils.Config

class MpesaFragment : Fragment() {

    private lateinit var fragmentMpesaBinding: FragmentMpesaBinding
    private val binding get() = fragmentMpesaBinding

    private lateinit var daraja: Daraja

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentMpesaBinding = FragmentMpesaBinding.inflate(layoutInflater, container, false)
        initialize()
        accessToken()
        return binding.root
    }

    private fun initialize() {
        // initialize daraja
        daraja = getDaraja()
        accessToken()
    }

    private fun getDaraja(): Daraja {

        return Daraja.builder(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
            .setBusinessShortCode(Config.BUSINESS_SHORTCODE)
            .setPassKey(AppUtils.passKey)
            .setTransactionType(Config.ACCOUNT_TYPE)
            .setCallbackUrl(Config.CALLBACK_URL)
            .setEnvironment(Environment.SANDBOX)
            .build()
    }

    private fun pay() {
        val number = binding.editTextNumber.text.toString()
        val amount = binding.editTextAmount.text.toString().toInt()
        initiatePayment(number, amount)
    }

    private fun initiatePayment(phoneNumber: String, amount: Int) {
        val token = AppUtils.getAccessToken(requireActivity())
        if (token == null) {
            accessToken()
            Toast.makeText(requireActivity(), "access token refreshed", Toast.LENGTH_SHORT).show()
        } else {
            // initiate payment
            Toast.makeText(requireActivity(), "wait", Toast.LENGTH_SHORT).show()
            daraja.initiatePayment(token, phoneNumber, amount.toString(), AppUtils.generateUUID(), "Payment") { darajaResult ->
                when (darajaResult) {
                    is DarajaResult.Success -> {
                        val result = darajaResult.value
                        Toast.makeText(requireActivity(), result.ResponseDescription, Toast.LENGTH_SHORT).show()
                    }
                    is DarajaResult.Failure -> {
                        val exception = darajaResult.darajaException
                        if (darajaResult.isNetworkError) {
                            Toast.makeText(requireActivity(), exception?.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireActivity(), exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun accessToken() {

        // get access token
        Toast.makeText(requireActivity(), "wait", Toast.LENGTH_SHORT).show()
        daraja.getAccessToken { darajaResult ->
            when (darajaResult) {
                is DarajaResult.Success -> {
                    val accessToken = darajaResult.value
                    AppUtils.saveAccessToken(requireContext(), accessToken.access_token)
                    binding.buttonSend.setOnClickListener { pay() }
                }
                is DarajaResult.Failure -> {
                    val darajaException = darajaResult.darajaException
                    Toast.makeText(requireActivity(), darajaException?.message, Toast.LENGTH_SHORT).show()
                    binding.buttonSend.setOnClickListener { accessToken() }
                }
            }
        }
    }



}