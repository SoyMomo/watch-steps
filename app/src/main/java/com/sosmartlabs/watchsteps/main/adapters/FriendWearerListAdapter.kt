package com.sosmartlabs.watchsteps.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sosmartlabs.watchsteps.OnFriendClickListener
import com.sosmartlabs.watchsteps.databinding.ItemFriendBinding
import com.sosmartlabs.watchsteps.main.data.model.FriendWearer

class FriendWearerListAdapter : ListAdapter<FriendWearer, FriendWearerListAdapter.FriendWearerViewHolder>(
    FriendWearerDiffCallback()
) {

    lateinit var listener: OnFriendClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendWearerViewHolder {

        return FriendWearerViewHolder(
            ItemFriendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }


    override fun onBindViewHolder(holder: FriendWearerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class FriendWearerViewHolder (private val binding: ItemFriendBinding, private val listener: OnFriendClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        //private lateinit var requestBuilder: RequestBuilder<PictureDrawable>

        fun bind(friendWearer: FriendWearer) {
            setData(friendWearer)
            setListener(friendWearer)
        }

        private fun setData(friendWearer: FriendWearer){

            if (friendWearer.isApproved) {
                binding.friendName.text = friendWearer.name(binding.root.context)
                if(friendWearer.color != null){
                    binding.friendColor.setColorFilter(friendWearer.color!!)
                }
                if(friendWearer.phoneNumber != null){
                    binding.friendName.text = friendWearer.phoneNumber
                } else {
                    //binding.friendSteps.text = binding.root.context.getString(R.string.no_phone_number)
                }

            }
            else {
                // Hide binding
                binding.friendName.visibility = View.GONE
                binding.friendColor.visibility = View.GONE
                 //(Placeholder)
            }
        }


        private fun setListener(friendWearer: FriendWearer) {
            binding.root.setOnClickListener {
                listener.onFriendClickedListener(friendWearer)
            }
        }

    }


}