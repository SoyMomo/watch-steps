package com.sosmartlabs.watchsteps.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosmartlabs.watchsteps.main.data.model.Avatar
import com.sosmartlabs.watchsteps.main.data.model.AvatarItem
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import com.sosmartlabs.watchsteps.main.data.repositories.WearerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WearerViewModel @Inject constructor(
    private val wearerRepository: WearerRepository,
): ViewModel() {

    private val _wearer = MutableLiveData<Wearer>()
    val wearer: LiveData<Wearer>
        get() = _wearer

    init {
        fetchWearer()
    }

    private fun fetchWearer() {
        viewModelScope.launch {
            runCatching {
                wearerRepository.getWearer()
            }.onFailure {
                Timber.e(it)
            }.onSuccess { wearer ->
                fetchAvatarItems(wearer)
                _wearer.postValue(wearer)
            }
        }
    }

    private fun fetchAvatarItems(wearer: Wearer) {
        wearer.avatar?.fetchIfNeeded<Avatar>()
        wearer.avatar?.hat?.fetchIfNeeded<AvatarItem>()
        wearer.avatar?.suit?.fetchIfNeeded<AvatarItem>()
        wearer.avatar?.accessory?.fetchIfNeeded<AvatarItem>()
    }

}