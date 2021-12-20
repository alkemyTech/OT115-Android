package com.alkemy.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkemy.ongandroid.model.ActivitiesResp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(): ViewModel() {

    private val _actList = MutableLiveData<List<ActivitiesResp>>(arrayListOf())
    val actList: LiveData<List<ActivitiesResp>> get() = _actList

    init {
        fetchdata()
    }

    private fun fetchdata(){
        _actList.value = listOf(
            ActivitiesResp(0,"Titulo generico","a"
                ,"lorem asd", "http://ongapi.alkemy.org//storage//FLQLzI8KqU.jpeg"
                ,0,0,"b","c","d",0),
            ActivitiesResp(0,"Segundo titulo generico","a"
                ,"lorem asd", "http://ongapi.alkemy.org//storage//ZNBGWQppLc.jpeg")
        )
    }
}