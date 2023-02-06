package com.sosmartlabs.watchsteps.main.data.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ktx.delegates.ParseDelegate

@ParseClassName("AvatarItem")
class AvatarItem: ParseObject() {
    var file by ParseDelegate<ParseFile>(null)
    var frame by ParseDelegate<ParseFile>(null)
    var name by ParseDelegate<String>(null)
    var type by ParseDelegate<String>(null)

}