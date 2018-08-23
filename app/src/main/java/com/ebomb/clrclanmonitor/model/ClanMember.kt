package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClanMember {
    var tag: String? = null
    var name: String? = null
    var role: String? = null
    var expLevel: Int? = 0
    var trophies: Int? = 0
    var clanRank: Int? = 0
    var previousClanRank: Int? = 0
    var donationsReceived: Int? = 0
    var clanChestPoints: Int? = 0

    @Expose
    @SerializedName("donations")
    var donations: Int? = 0
}