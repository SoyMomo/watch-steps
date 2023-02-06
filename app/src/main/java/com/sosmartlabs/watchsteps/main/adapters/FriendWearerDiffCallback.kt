package com.sosmartlabs.watchsteps.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.sosmartlabs.watchsteps.main.data.model.FriendWearer

class FriendWearerDiffCallback: DiffUtil.ItemCallback<FriendWearer>() {
    override fun areItemsTheSame(oldItem: FriendWearer, newItem: FriendWearer): Boolean {
        return oldItem.deviceId == newItem.deviceId
    }

    override fun areContentsTheSame(oldItem: FriendWearer, newItem: FriendWearer): Boolean {
        return oldItem == newItem
    }
}
