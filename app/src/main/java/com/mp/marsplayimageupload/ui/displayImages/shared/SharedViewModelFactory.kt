package com.mp.marsplayimageupload.ui.displayImages.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mp.marsplayimageupload.data.firebase.Repository

@Suppress("UNCHECKED_CAST")
class SharedViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(repository) as T
    }
}