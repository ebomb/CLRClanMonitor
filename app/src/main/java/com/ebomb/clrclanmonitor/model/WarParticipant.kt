package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WarParticipant {
    @Expose
    @SerializedName("tag")
    var tag: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("cardsEarned")
    var cardsEarned: Int? = 0

    @Expose
    @SerializedName("battlesPlayed")
    var battlesPlayed: Int? = 0

    @Expose
    @SerializedName("wins")
    var wins: Int? = 0
}