package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.model.Member
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsFragmentViewModel @Inject constructor() : ViewModel() {
    private val _memberList = MutableLiveData<List<Member>>(arrayListOf())
    val memberList: LiveData<List<Member>> get() = _memberList

    init {
        fetchData()
    }

    private fun fetchData() {
        _memberList.value = listOf(
            Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 1", "Job 1"),
            Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 3", "Job 3"),
            Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 4", "Job 4"),
            Member(null, "Name 5", "Job 5")
        )
    }
}