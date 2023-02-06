package com.sosmartlabs.watchsteps.main.data.model

import android.content.Context
import android.os.Parcelable
import com.sosmartlabs.watchsteps.R

@kotlinx.parcelize.Parcelize
data class FriendWearer(
    var objectId: String,
    var deviceId: String,
    var firstName: String?,
    var lastName: String?,
    var isApproved: Boolean,
    var avatar: Avatar? = null,
    var hearts: Int? = null,
    var phoneNumber: String?,
    var color: Int? = null,
    ): Parcelable {

    override fun toString(): String {
        return "objectID: $objectId, deviceId: $deviceId, name: $firstName $lastName, isApproved: $isApproved, hearts: $hearts, phoneNumber: $phoneNumber, avatar: ${isAvatarNull(avatar)}"
    }

    private fun isAvatarNull(avatar: Avatar?): String {
        return if (avatar == null) {
            "null"
        } else {
            "not null"
        }
    }

    fun name(context: Context): String{
        return if (firstName != null && lastName != null) {
            "$firstName $lastName"
        } else if (firstName != null) {
            "$firstName"
        }  else if (lastName != null) {
            "$lastName"
        } else {
            context.getString(R.string.contact_no_name)
        }
    }

}