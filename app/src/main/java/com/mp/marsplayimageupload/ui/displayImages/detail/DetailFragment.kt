package com.mp.marsplayimageupload.ui.displayImages.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

import com.mp.marsplayimageupload.R
import com.mp.marsplayimageupload.databinding.DetailFragmentBinding
import com.mp.marsplayimageupload.databinding.FragmentPreviewBinding
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModel
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModelFactory
import com.mp.marsplayimageupload.utils.FLAGS_FULLSCREEN
import com.mp.marsplayimageupload.utils.IMMERSIVE_FLAG_TIMEOUT
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailFragment : Fragment(),KodeinAware {

    companion object {
        fun newInstance() = DetailFragment()
    }
    override val kodein by kodein()
    private val factory by instance<SharedViewModelFactory>()
    private lateinit var viewModel: SharedViewModel
    private lateinit var container: PhotoView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: DetailFragmentBinding =
            DataBindingUtil.inflate(inflater,R.layout.detail_fragment,container,false)
        val view:View=binding.root
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(SharedViewModel::class.java)
        binding.image=viewModel.selectedImage.value
        binding.lifecycleOwner=requireActivity()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view as PhotoView
    }

    override fun onResume() {
        super.onResume()
        requireActivity().container.postDelayed({
            container.systemUiVisibility = FLAGS_FULLSCREEN
        }, 500L)
    }


}
