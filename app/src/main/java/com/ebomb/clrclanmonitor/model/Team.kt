package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Team {
    @Expose
    @SerializedName("clan")
    var clan: TeamClan? = null
}
