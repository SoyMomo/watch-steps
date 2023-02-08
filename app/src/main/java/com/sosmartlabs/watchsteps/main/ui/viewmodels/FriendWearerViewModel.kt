package com.sosmartlabs.watchsteps.main.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosmartlabs.watchsteps.main.data.model.FriendWearer
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import com.sosmartlabs.watchsteps.main.data.repositories.FriendWearerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FriendWearerViewModel @Inject constructor(
    private val friendWearerRepository: FriendWearerRepository,
): ViewModel() {

    private val _friendWearers = MutableLiveData<List<FriendWearer>>()
    val friendWearers: LiveData<List<FriendWearer>>
        get() = _friendWearers

    fun fetchFriendWearers(wearer: Wearer){
        viewModelScope.launch {
            Timber.d("loadData: ")
            runCatching {
                friendWearerRepository.getFriends(wearer)
            }.onFailure {
                Timber.e(it)
            }.onSuccess { friendWearers ->
                _friendWearers.postValue(friendWearers)
            }
        }
    }
}