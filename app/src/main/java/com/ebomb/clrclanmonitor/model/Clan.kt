package com.ebomb.clrclanmonitor.model

class Clan {
    var tag: String? = null
    var name: String? = null
    var type: String? = null
    var description: String? = null
    var clanScore: Int? = 0
    var requiredTrophies: Int? = 0
    var donationsPerWeek: Int? = 0
    var clanChestStatus: String? = null
    var clanChestLevel: Int? = 0
    var clanChestMaxLevel: Int? = 0
    var members: Int? = 0
    var memberList: List<ClanMember>? = null
}