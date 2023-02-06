package com.sosmartlabs.watchsteps.main.data.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ktx.delegates.ParseDelegate

@ParseClassName("Wearer")
class Wearer: ParseObject() {
    var firstName by ParseDelegate<String?>(null)
    var lastName by ParseDelegate<String?>(null)
    var image by ParseDelegate<ParseFile?>(null)
    var hardwareModel by ParseDelegate<String?>(null)
    var deviceId by ParseDelegate<String>(null)
    var avatar by ParseDelegate<Avatar?>(null)
    var hearts by ParseDelegate<Int?>(null)
    var phoneNumber by ParseDelegate<String?>(null)
    var systemColor by ParseDelegate<String?>(null)
}