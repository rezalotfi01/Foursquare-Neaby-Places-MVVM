package com.reza.mymvvm.pages.placeslist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reza.mymvvm.databinding.ListItemPlacesBinding
import com.reza.mymvvm.pages.placeslist.data.Place

class PlacesListAdapter : PagedListAdapter<Place, PlacesListAdapter.ViewHolder>(
    PlacesDiffCallback()
) {

    var onClickItem: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemPlacesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val placeItem = getItem(position)
        placeItem?.let {
            holder.apply {
                bind(createOnClickListener(position), placeItem)
                itemView.tag = placeItem
            }
        }
    }

    private fun createOnClickListener(position: Int) = View.OnClickListener {
        onClickItem.invoke(position)
    }

    class ViewHolder(private val binding: ListItemPlacesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Place) {
            binding.apply {
                clickListener = listener
                place = item
                executePendingBindings()
            }
        }

    }
}

private class PlacesDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place) =  oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Place, newItem: Place) = oldItem.url == newItem.url
}