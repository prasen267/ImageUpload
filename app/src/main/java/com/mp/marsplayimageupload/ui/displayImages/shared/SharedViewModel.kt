package com.mp.marsplayimageupload.ui.displayImages.shared

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mp.marsplayimageupload.R
import com.mp.marsplayimageupload.data.firebase.Repository
import com.mp.marsplayimageupload.data.listeners.FetchDataListener
import com.mp.marsplayimageupload.data.listeners.uploadListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel(val repository:Repository):ViewModel() {

    val selectedImage=MutableLiveData<String>()
    val listImages=MutableLiveData<ArrayList<String>>()
    val previewImage1=MutableLiveData<String>()

    fun getImages(fetchDataListener: FetchDataListener){
        CoroutineScope(Dispatchers.IO).launch {
            //delay(500L)
            val data=repository.getFiles(fetchDataListener)

            //listImages.postValue(data)
        }
    }

    fun uploadImage(uri: Uri,uploadListener: uploadListener){
        CoroutineScope(Dispatchers.IO).launch {
            repository.uploadFile(uri,uploadListener)

        }
    }
   /* fun getImageList():MutableLiveData<Array<Int>> {
        val list= arrayOf(
            R.drawable.background1,
            R.drawable.background2,
            R.drawable.background3,
            R.drawable.background4,
            R.drawable.background5,
            R.drawable.background6,
            R.drawable.background7
        )
        return MutableLiveData(list)
    }*/

}