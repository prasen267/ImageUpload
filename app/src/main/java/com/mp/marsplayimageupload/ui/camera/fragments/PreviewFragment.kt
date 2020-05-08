package com.mp.marsplayimageupload.ui.camera.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mp.marsplayimageupload.R
import com.mp.marsplayimageupload.data.listeners.uploadListener
import com.mp.marsplayimageupload.databinding.FragmentPreviewBinding
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModel
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModelFactory
import kotlinx.android.synthetic.main.fragment_preview.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.w3c.dom.Text


class PreviewFragment : Fragment(), KodeinAware, uploadListener {
    override val kodein by kodein()
    private val factory by instance<SharedViewModelFactory>()
    private lateinit var viewModel: SharedViewModel
    private lateinit var update_textview:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding:FragmentPreviewBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_preview,container,false)
        val view:View=binding.root
        (requireActivity()).title="Upload Image"
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(SharedViewModel::class.java)
        update_textview=view.findViewById(R.id.update_textview)
        var uri: Uri? = null
        arguments?.let {
            uri = PreviewFragmentArgs.fromBundle(it).imageUri
            if (uri == null) {
                uri = it.getParcelable("uri")
            }
        }
        binding.viewModel=uri.toString()
        binding.lifecycleOwner=requireActivity()

        update_textview.setOnClickListener {
            viewModel.uploadImage(uri!!, this)
        }


        return view
    }



    override fun uploadStarted() {
        progress_bar.post { progress_bar.visibility = View.VISIBLE }

    }

    override fun uploadCompleted() {
        progress_bar.post {
            progress_bar.visibility = View.GONE
            val action = PreviewFragmentDirections.actionToDisplayFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

        // DisplayFragmentDirections.actionDisplayFragmentToDetailFragment()
        //val action=
    }

    override fun uploadFailed(msg: String) {
        progress_bar.post {
            progress_bar.visibility = View.GONE
            Toast.makeText(requireContext(), "Upload Failed: " + msg, Toast.LENGTH_LONG).show()
        }
    }
}
