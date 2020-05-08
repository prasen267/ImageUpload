package com.mp.marsplayimageupload.data.firebase

import android.net.Uri
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mp.marsplayimageupload.data.listeners.FetchDataListener
import com.mp.marsplayimageupload.data.listeners.uploadListener
import com.mp.marsplayimageupload.utils.STORAGE_PATH_UPLOAD
import com.mp.marsplayimageupload.utils.getFileExtension

class FireBaseSource {
    companion object {
        fun removeListeners() {
            //  storage
        }
    }

    private val storageRef: StorageReference by lazy {
        FirebaseStorage.getInstance().getReference()
    }
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference(STORAGE_PATH_UPLOAD)
    }
    val uploadListener: uploadListener? = null
    val fetchListener: FetchDataListener? = null
    fun uploadFile(uri: Uri, uploadListener: uploadListener) {
        uploadListener.uploadStarted()
        val mRef: StorageReference = storageRef.child(
            STORAGE_PATH_UPLOAD + System.currentTimeMillis() +
                    getFileExtension(uri.toString())
        )

        mRef.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {

                    uploadListener.uploadFailed(it.message.toString())
                    throw it
                }
            }
            mRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val key = database.push().key
                database.child(key!!).setValue(downloadUri.toString())
                uploadListener.uploadCompleted()
            } else {
                // Handle failures
                // ...
                uploadListener.uploadFailed("upload Failed")
            }
        }

    }

    fun getFile(fetchDataListener: FetchDataListener) {

        fetchDataListener.fetchStarted()
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                fetchDataListener.fetchFailed()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val pictureURLList = ArrayList<String>()
                for (postSnapshot in snapshot.getChildren()) {
                    val pictureURL = postSnapshot.value as String
                    pictureURLList.add(pictureURL)
                }
                fetchDataListener.fetchCompleted(pictureURLList)
            }

        })
    }
}



   // fun removeListener()
