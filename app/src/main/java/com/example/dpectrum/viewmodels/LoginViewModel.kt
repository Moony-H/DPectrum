package com.example.dpectrum.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dpectrum.R
import com.example.dpectrum.data.ContentServiceRepository
import com.example.dpectrum.data.LoginServiceRepository
import com.example.dpectrum.data.TutorContent
import com.example.dpectrum.other.SingleLiveData
import com.example.dpectrum.tag.FragmentTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginServiceRepository: LoginServiceRepository,
    private val contentServiceRepository: ContentServiceRepository
):ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _allContents=MutableLiveData<List<TutorContent>>()
    val allContents:LiveData<List<TutorContent>>
        get()=_allContents

    private val _sortedContents=MutableLiveData<List<TutorContent>>()
    val sortedContents:LiveData<List<TutorContent>>
        get()=_sortedContents


    private val _currentPageTag=MutableLiveData<FragmentTag>()
    val currentPageTag:LiveData<FragmentTag>
        get()=_currentPageTag


    private val _selectedContents=SingleLiveData<TutorContent>()
    val selectedContent:LiveData<TutorContent>
        get()=_selectedContents

    var photo:Bitmap?=null

    init {
        Log.d("LoginViewModel", "created")
    }

    fun login(password: String, phone: String) {
        Log.d("LoginViewModel", "login Start")
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val response = loginServiceRepository.postLoginBody(password, phone)

            if (response.code() == 200) {
                Log.d("LoginViewModel", "login success token: ${response.body()?.token}")
                _token.postValue(response.body()?.token)
            } else if (response.code() == 400) {
                Log.d(
                    "LoginViewModel",
                    "login params error, code: ${response.code()}, token:${response.body()?.token}, message:${response.body()?.message}"
                )
                _token.postValue("null")

            } else {
                Log.d("LoginViewModel", "login error, code: ${response.code()}")
            }
        }
    }

    fun getContents(){
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler){
            token.value?.let {
                val response=contentServiceRepository.getContents("Bearer $it","0")

                if (response.code() == 200) {
                    Log.d("LoginViewModel", "content success")
                    Log.d("test","${response.body()}")
                    _allContents.postValue(response.body())
                } else if (response.code() == 400) {
                    Log.d(
                        "LoginViewModel",
                        "content params error, code: ${response.code()}"
                    )


                } else {
                    Log.d("LoginViewModel", "content error, code: ${response.code()}")
                }
            }
        }

    }

    fun setSortedContents(list:List<TutorContent>){
        _sortedContents.value=list
    }

    fun setSelectedContents(content: TutorContent){
        _selectedContents.value=content
    }


    fun setCurrentPage(menuItemId:Int):Boolean{
        //val pageTag=getPageTag(menuItemId)
        //changeCurrentPage(pageTag)
        return true
    }
    private fun changeCurrentPage(pageType: FragmentTag) {
        if (_currentPageTag.value == pageType)
            return

        _currentPageTag.value = pageType
    }
    private fun getPageTag(page_num:Int):FragmentTag{
        return when(page_num){
            R.id.item_page_home-> FragmentTag.FRAGMENT_HOME_HOME
            R.id.item_page_tutor-> FragmentTag.FRAGMENT_HOME_TUTOR
            R.id.item_page_chat-> FragmentTag.FRAGMENT_HOME_CHAT
            R.id.item_page_my->FragmentTag.FRAGMENT_HOME_MY
            else -> throw IllegalArgumentException("bottom navi view not found: fragment tag")
        }

    }


    private val coroutineExceptionHandler= CoroutineExceptionHandler{_, throwable->
        throwable.printStackTrace()

    }

}