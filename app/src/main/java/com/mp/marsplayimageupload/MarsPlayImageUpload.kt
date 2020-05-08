package com.mp.marsplayimageupload

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.google.firebase.database.FirebaseDatabase
import com.mp.marsplayimageupload.data.firebase.FireBaseSource
import com.mp.marsplayimageupload.data.firebase.Repository
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MarsPlayImageUpload:Application(),CameraXConfig.Provider,KodeinAware {

    override val kodein= Kodein.lazy {
        import(androidXModule(this@MarsPlayImageUpload))
        bind() from singleton { FireBaseSource()}
        bind() from singleton { Repository(instance()) }
        bind() from provider { SharedViewModelFactory(instance()) }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}