package com.mp.marsplayimageupload.data.firebase

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.mp.marsplayimageupload.data.listeners.FetchDataListener
import com.mp.marsplayimageupload.data.listeners.uploadListener

class Repository(private val fireBase: FireBaseSource) {

    val imagelist = MutableLiveData<ArrayList<String>>()

    init {

    }

    suspend fun uploadFile(uri: Uri, uploadListener: uploadListener) {

        fireBase.uploadFile(uri, uploadListener)
    }

    suspend fun getFiles(fetchDataListener: FetchDataListener) {

        val data = fireBase.getFile(fetchDataListener)
       // return data

    }
}