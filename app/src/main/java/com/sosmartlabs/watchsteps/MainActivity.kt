package com.sosmartlabs.watchsteps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sosmartlabs.watchsteps.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setToolbar()
    }

    private fun setToolbar() {
        mainBinding.toolbarBack.setOnClickListener {
            super.onBackPressed()
        }
    }
}