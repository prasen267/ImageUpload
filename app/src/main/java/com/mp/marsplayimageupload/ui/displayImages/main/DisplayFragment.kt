package com.mp.marsplayimageupload.ui.displayImages.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mp.marsplayimageupload.R
import com.mp.marsplayimageupload.data.listeners.FetchDataListener
import com.mp.marsplayimageupload.ui.camera.fragments.CameraFragmentDirections
import com.mp.marsplayimageupload.ui.camera.fragments.PermissionsFragment
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModel
import com.mp.marsplayimageupload.ui.displayImages.shared.SharedViewModelFactory
import kotlinx.android.synthetic.main.display_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DisplayFragment : Fragment(), DisplayRecyclerAdapter.DisplayItemListener, KodeinAware,
    FetchDataListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    override val kodein by kodein()
    private val factory by instance<SharedViewModelFactory>()
     private lateinit var progress_bar_display:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.display_fragment, container, false)
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(SharedViewModel::class.java)
        progress_bar_display=view.findViewById(R.id.progress_bar_display)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeLayout = view.findViewById(R.id.swipeLayout)
        viewModel.getImages(this)
        swipeLayout.setOnRefreshListener {
            viewModel.getImages(this)
        }
      /*  container?.postDelayed({
            viewModel.getImages(this)
        },500L)
*/
        // recyclerView.layoutManager=GridLayoutManager(requireContext(),2)

        return view
    }



    override fun onItemClick(result: String) {
        viewModel.selectedImage.value = result
        val action = DisplayFragmentDirections.actionDisplayFragmentToDetailFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun fetchStarted() {
        progress_bar_display.post {
            progress_bar_display.visibility = View.VISIBLE
        }
    }

    override fun fetchCompleted(pictureURLList: ArrayList<String>) {
        viewModel.listImages.postValue(pictureURLList)
        progress_bar_display.post {
            progress_bar_display.visibility = View.GONE

            viewModel.listImages.observe(viewLifecycleOwner, Observer { images ->
                recyclerView.also {

                   if(images.size>0){

                       it.layoutManager = GridLayoutManager(requireContext(), 2)
                       it.adapter = DisplayRecyclerAdapter(images, this)
                       (it.adapter as DisplayRecyclerAdapter).notifyDataSetChanged()
                       swipeLayout.isRefreshing = false
                   }else
                    {
                        it.visibility=View.GONE
                        emptyTV.visibility=View.VISIBLE
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(
                DisplayFragmentDirections.actionDisplayToPermission()
            )
        }
    }
    override fun fetchFailed() {

    }

}
