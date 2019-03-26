package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlayerBattle {
    @Expose
    @SerializedName("team")
    var team: List<Team>? = null
}
