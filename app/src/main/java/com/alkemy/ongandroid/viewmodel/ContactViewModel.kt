package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.businesslogic.managers.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ContactViewModel @Inject constructor(private val validator: Validator) : ViewModel() {
    private val _canSubmit = MutableLiveData(false)
    val canSubmit: LiveData<Boolean> get() = _canSubmit

    private val _isFullNameValid = MutableLiveData<Boolean>()
    val isFullNameValid: LiveData<Boolean> get() = _isFullNameValid

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean> get() = _isValidEmail

    private val _isMessageValid = MutableLiveData<Boolean>()
    val isMessageValid: LiveData<Boolean> get() = _isMessageValid


    fun validateFields(fullName: String, email: String, message: String) {
        _isFullNameValid.value = fullName.isNotEmpty() && fullName.isNotBlank()
        _isMessageValid.value = message.isNotEmpty() && message.isNotBlank()
        _isValidEmail.value = validator.validateEmail(email)
        _canSubmit.value =
            isFullNameValid.value == true && isValidEmail.value == true && isMessageValid.value == true
    }
}