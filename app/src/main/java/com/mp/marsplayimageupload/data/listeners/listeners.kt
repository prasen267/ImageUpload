package com.mp.marsplayimageupload.data.listeners

interface uploadListener {
    fun uploadStarted()
    fun uploadCompleted()
    fun uploadFailed(msg: String)
}

interface FetchDataListener {
    fun fetchStarted()
    fun fetchCompleted(pictureURLList: ArrayList<String>)
    fun fetchFailed()
}