package com.ebomb.clrclanmonitor.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class War {
    var seasonId: Int? = 0
    var createdDate: String? = null
    var participants: List<WarParticipant>? = null
}