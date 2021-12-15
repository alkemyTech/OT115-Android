package com.alkemy.ongandroid.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ContactFragmentBinding
import com.alkemy.ongandroid.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

    private val viewModel by viewModels<ContactViewModel>()
    private lateinit var binding: ContactFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        initComponents()
    }

    private fun initComponents() {
        binding.fullName.addTextChangedListener(onTextChanged())
        binding.email.addTextChangedListener(onTextChanged())
        binding.message.addTextChangedListener(onTextChanged())
    }

    private fun setUpObservers() {
        viewModel.canSubmit.observe(viewLifecycleOwner) {
            if (it) {
                enableSendButton()
            } else {
                disableSendButton()
            }
        }

        viewModel.isFullNameValid.observe(viewLifecycleOwner) {
            binding.contactFullName.error = getString(R.string.empty_field_message)
            binding.contactFullName.isErrorEnabled = !it
        }

        viewModel.isValidEmail.observe(viewLifecycleOwner) {
            binding.contactEmail.error = getString(R.string.invalid_email_message)
            binding.contactEmail.isErrorEnabled = !it
        }

        viewModel.isMessageValid.observe(viewLifecycleOwner) {
            binding.contactMessage.error = getString(R.string.empty_field_message)
            binding.contactMessage.isErrorEnabled = !it
        }
    }

    private fun enableSendButton() {
        binding.sendButton.apply {
            isEnabled = true
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.ong_color))
            setTextColor(Color.WHITE)
        }
    }

    private fun disableSendButton() {
        binding.sendButton.apply {
            isEnabled = false
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_ong_color))
            setTextColor(Color.LTGRAY)
        }
    }

    private fun onTextChanged() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
            viewModel.validateFields(
                fullName = binding.fullName.text.toString(),
                email = binding.email.text.toString(),
                message = binding.message.text.toString()
            )
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}