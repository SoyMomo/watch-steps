package com.sosmartlabs.watchsteps.main.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sosmartlabs.watchfriends.utils.NetworkStateChecker
import com.sosmartlabs.watchsteps.OnFriendClickListener
import com.sosmartlabs.watchsteps.databinding.FragmentMainBinding
import com.sosmartlabs.watchsteps.main.adapters.FriendWearerListAdapter
import com.sosmartlabs.watchsteps.main.data.model.FriendWearer
import com.sosmartlabs.watchsteps.main.data.model.Wearer
import com.sosmartlabs.watchsteps.main.ui.viewmodels.FriendWearerViewModel
import com.sosmartlabs.watchsteps.main.ui.viewmodels.PedometerViewModel
import com.sosmartlabs.watchsteps.main.ui.viewmodels.WearerViewModel
import com.sosmartlabs.watchsteps.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainAppStepsFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: FriendWearerListAdapter

    //private val viewModel: MainAppStepsViewModel by viewModels()
    private val contactViewModel: FriendWearerViewModel by viewModels()
    private val wearerViewModel: WearerViewModel by viewModels()
    private val pedometerViewModel: PedometerViewModel by viewModels()

    lateinit var wearer: Wearer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: ")

        // Receiver for real time contacts
        requireContext().registerReceiver(refreshContactsReceiver, IntentFilter("${Constants.pkgSoyMomoBrainCommons}.refreshContacts"))

        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: ")
        requireContext().unregisterReceiver(refreshContactsReceiver)
        pedometerViewModel.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Check for internet connection
        if (NetworkStateChecker.isThereInternetConnection(requireContext())) {
            setRecyclerView()
            observeViewModel()
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            Timber.d("No internet connection")
            // Set no internet connection view
            binding.recyclerView.visibility = View.GONE
        }

    }

    private fun setRecyclerView() {
        adapter = FriendWearerListAdapter().apply {
            listener = object : OnFriendClickListener {
                override fun onFriendClickedListener(friendWearer: FriendWearer) {
                    Timber.d("onFriendClickedListener: $friendWearer")
                }
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        wearerViewModel.wearer.observe(viewLifecycleOwner) {
            Timber.d("observeViewModel: wearer: $it")
            wearer = it
            contactViewModel.fetchFriendWearers(it)
        }
        contactViewModel.friendWearers.observe(viewLifecycleOwner) {
            Timber.d("observeViewModel: friendWearers: $it")
            adapter.submitList(it)
            if(contactViewModel.friendWearers.value!!.isEmpty()){
                Timber.d("onReceive() - No contacts")
                binding.recyclerView.visibility = View.GONE
                //binding.empty.visibility = View.VISIBLE
            }
        }
    }

    private val refreshContactsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Timber.d("onReceive()")
            contactViewModel.fetchFriendWearers(wearer)
            Timber.d("friends: ${contactViewModel.friendWearers.value}")
            if(contactViewModel.friendWearers.value!!.isEmpty()){
                Timber.d("onReceive() - No contacts")
                binding.recyclerView.visibility = View.GONE
                //binding.empty.visibility = View.VISIBLE
            }
        }
    }

    private fun setupViewModel() {
        // TODO: Use the Pedometer ViewModel
        pedometerViewModel.start()
    }
}
