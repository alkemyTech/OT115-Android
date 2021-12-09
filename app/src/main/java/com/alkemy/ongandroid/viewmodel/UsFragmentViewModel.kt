package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.model.Member
import com.alkemy.ongandroid.model.RowMembers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsFragmentViewModel @Inject constructor() : ViewModel() {
    private val _memberList = MutableLiveData<List<RowMembers>>(arrayListOf())
    val memberList: LiveData<List<RowMembers>> get() = _memberList

    init {
        fetchData()
    }

    private fun fetchData() {
        _memberList.value = listOf(
            RowMembers(
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 1", "Job 1"),
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 2", "Job 2"),
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 3", "Job 3")
            ),
            RowMembers(
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 4", "Job 4"),
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 5", "Job 5"),
                Member("http://ongapi.alkemy.org/storage/QRSfhgYnYl.png", "Name 6", "Job 6")
            )
        )
    }
}