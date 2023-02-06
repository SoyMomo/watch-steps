package com.sosmartlabs.watchsteps.main.data.repositories

import android.graphics.Color.parseColor
import com.parse.ParseQuery
import com.parse.ktx.findAll
import com.sosmartlabs.watchsteps.main.data.model.FriendWearer
import com.sosmartlabs.watchsteps.main.data.model.WatchWearer
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendWearerRepository @Inject constructor(
) {

    fun getFriends(wearer: Wearer): List<FriendWearer> {
        val friendWearers = getFriendWearers(wearer).filter { it.isApproved }
        val reverseFriendWearers = getReverseFriendWearers(wearer).filter { it.isApproved }

        return friendWearers + reverseFriendWearers

    }

    private fun getFriendWearers(wearer: Wearer): List<FriendWearer>{
        val query = ParseQuery.getQuery(WatchWearer::class.java).include("watch1").include("watch2")
        query.whereEqualTo("watch1", wearer)
        val watchFriends = query.findAll()
        Timber.d("Watch friends: $watchFriends")
        kotlin.runCatching {
            return watchFriends.map { watchFriend ->
                FriendWearer(
                    watchFriend.watch2!!.objectId,
                    watchFriend.watch2!!.deviceId,
                    watchFriend.watch2?.firstName,
                    watchFriend.watch2?.lastName,
                    watchFriend.isWatch1Approved && watchFriend.isWatch2Approved,
                    watchFriend.watch2?.avatar,
                    watchFriend.watch2?.hearts,
                    watchFriend.watch2?.phoneNumber,
                    parseColor((watchFriend.watch2?.systemColor ?: "#000000") as String?)
                )
            }
        }.onFailure {
            Timber.d("Error getting friends: ${it.message}")
            return emptyList()
        }
        Timber.e("Error getting friends")
        return emptyList()
    }

    private fun getReverseFriendWearers(wearer: Wearer): List<FriendWearer>{
        val query = ParseQuery.getQuery(WatchWearer::class.java).include("watch1").include("watch2")
        query.whereEqualTo("watch2", wearer)
        val watchFriends = query.findAll()
        return watchFriends.map { watchFriend ->
            FriendWearer(watchFriend.watch1!!.objectId,
                watchFriend.watch1!!.deviceId,
                watchFriend.watch1?.firstName,
                watchFriend.watch1?.lastName,
                watchFriend.isWatch1Approved && watchFriend.isWatch2Approved,
                watchFriend.watch1?.avatar,
                watchFriend.watch1?.hearts,
                watchFriend.watch1?.phoneNumber,
                parseColor((watchFriend.watch1?.systemColor ?: "#000000") as String?))
        }
    }



}