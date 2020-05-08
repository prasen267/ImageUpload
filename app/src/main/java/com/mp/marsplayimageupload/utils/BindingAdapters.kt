package com.mp.marsplayimageupload.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if(imageUrl!=null) {
        //val url: String
       // url = "https://www.tripmaza.com/Images/Airlinepng/" + imageUrl + ".png"
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}
@BindingAdapter("photoViewUrl")
fun loadPhotoView(view: PhotoView, imageUrl: String?) {
    if(imageUrl!=null) {
        //val url: String
        // url = "https://www.tripmaza.com/Images/Airlinepng/" + imageUrl + ".png"
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}