package com.sosmartlabs.watchsteps.main.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.ParseDelegate

@ParseClassName("Avatar")
class Avatar: ParseObject() {
    var hat by ParseDelegate<AvatarItem?>("hat")
    var suit by ParseDelegate<AvatarItem?>("suit")
    var accessory by ParseDelegate<AvatarItem?>("accessory")
}