package com.sosmartlabs.watchsteps.main.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.ParseDelegate

@ParseClassName("WatchWearer")
class WatchWearer: ParseObject() {
    var watch1 by ParseDelegate<Wearer?>(null)
    var watch2 by ParseDelegate<Wearer?>(null)
    var isWatch1Approved by ParseDelegate<Boolean>(null)
    var isWatch2Approved by ParseDelegate<Boolean>(null)
}