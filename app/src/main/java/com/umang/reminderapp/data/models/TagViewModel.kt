package com.umang.reminderapp.data.models

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umang.reminderapp.data.classes.Tag
import com.umang.reminderapp.singletons.TagManager
import kotlinx.coroutines.launch

class TagViewModel: ViewModel() {

    private var _tagList = MutableLiveData<SnapshotStateList<String>>()
    var tagList : LiveData<SnapshotStateList<String>> = _tagList

    init {
        getAllTags()
    }

    fun getAllTags(){
        viewModelScope.launch {
            _tagList.value = TagManager.getAllTags()
        }
        Log.d("TagViewModel", "getAllTags: ${tagList.value.toString()}")
    }

    fun getTag(name: String): String?{
        return TagManager.getTag(name)
    }

    fun addTag(
        name: String = " Tag"
    ) {
        TagManager.addTag(name)
        this.viewModelScope.launch { getAllTags() }
        Log.d("TagViewModel", "addTag: ${tagList.value.toString()}")
    }

    fun deleteTag(name: String){
        TagManager.deleteTag(name)
        viewModelScope.launch {
            getAllTags()
        }
    }

    fun createDummyTags(){
        TagManager.createDummyTags()
    }

}