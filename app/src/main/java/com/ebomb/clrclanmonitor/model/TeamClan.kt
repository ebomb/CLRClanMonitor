package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamClan {
    @Expose
    @SerializedName("tag")
    var tag: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null


}
