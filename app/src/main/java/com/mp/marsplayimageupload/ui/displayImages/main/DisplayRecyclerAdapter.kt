package com.mp.marsplayimageupload.ui.displayImages.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mp.marsplayimageupload.R
import com.mp.marsplayimageupload.databinding.DisplayGridItemBinding

class DisplayRecyclerAdapter(private val images: ArrayList<String>,
                             val itemClickListener:DisplayItemListener) :
    RecyclerView.Adapter<DisplayRecyclerAdapter.DisplayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DisplayViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.display_grid_item,
            parent,
            false
        )
    )

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount()=images.size

    override fun onBindViewHolder(holder: DisplayViewHolder, position: Int) {
       val image=images[position]
        holder.displayGridItemBinding.viewModel=image
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(image)
        }
    }


    inner class DisplayViewHolder(val displayGridItemBinding: DisplayGridItemBinding) :
        RecyclerView.ViewHolder(displayGridItemBinding.root)
    interface DisplayItemListener {
        fun onItemClick(result: String)
    }
}