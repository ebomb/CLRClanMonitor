package com.ebomb.clrclanmonitor.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ebomb.clrclanmonitor.R
import com.ebomb.clrclanmonitor.model.ClanMember
import com.ebomb.clrclanmonitor.model.Warlog
import java.util.*


class ClanMonitorAdapter(private var clanMembers: List<ClanMember>?, private var warlog: Warlog?) : RecyclerView.Adapter<ClanMonitorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClanMonitorAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val searchResultView = inflater.inflate(R.layout.item_member_status, parent, false)
        return ViewHolder(searchResultView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(position)
    }

    override fun getItemCount(): Int {
        return clanMembers!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.name)
        lateinit var name: TextView

        @BindView(R.id.war_battles)
        lateinit var warBattles: TextView

        @BindView(R.id.war_wins)
        lateinit var warWins: TextView

        @BindView(R.id.war_cards_received)
        lateinit var warCards: TextView

        @BindView(R.id.donation_giver)
        lateinit var donationsGave: TextView

        @BindView(R.id.donation_receiver)
        lateinit var donationsReceived: TextView

        @BindView(R.id.status)
        lateinit var status: TextView

        @BindView(R.id.war_win_lose_ratio)
        lateinit var warWinPercentage: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(position: Int) {
            val member = clanMembers?.get(position)
            val donationsReceivedCount = member?.donationsReceived
            val donationsGaveCount = member?.donations

            name.text = member?.name.toString()
            donationsGave.text = donationsGaveCount.toString()
            donationsReceived.text = donationsReceivedCount.toString()

            setWarDetailsView(member)
            setStatusView(member)
        }

        private fun setStatusView(member: ClanMember?) {
            var statusText = "KICK!"
            var color = itemView.resources.getColor(R.color.red, null)
            val donationGaveCount = multiplyBasedOnDay(member?.donations)
            val donationReceivedCount = multiplyBasedOnDay(member?.donationsReceived)
            val warBattles = warBattles.text.toString().toInt()
            val warWins = warWins.text.toString().toInt()
            val warCards = warCards.text.toString().toInt()

            if (donationGaveCount != null && donationReceivedCount != null) {
                when {
                    (donationGaveCount > 600 && donationReceivedCount > 600)
                            || (warWins > 4 || warBattles > 10 || warCards > 3000) -> {
                        statusText = "E.A."
                        color = itemView.resources.getColor(R.color.green_dark, null)
                    }
                    (donationGaveCount > 400 && donationReceivedCount > 400)
                            || (warWins > 3 || warBattles > 8 || warCards > 2000) -> {
                        statusText = "V.A."
                        color = itemView.resources.getColor(R.color.green, null)
                    }
                    (donationGaveCount > 300 && donationReceivedCount > 300)
                            || (warWins > 2 || warBattles > 4 || warCards > 1000) -> {
                        statusText = "Active"
                        color = itemView.resources.getColor(R.color.green, null)
                    }
                    (donationGaveCount > 200 && donationReceivedCount > 200)
                            || (warWins > 1 || warBattles > 2 || warCards > 500) -> {
                        statusText = "L.A."
                        color = itemView.resources.getColor(R.color.orange, null)
                    }
                }
            }
            status.text = statusText
            status.setTextColor(color)
        }

        private fun setWarDetailsView(member: ClanMember?) {
            var warBattlesCount = 0
            var warWinsCount = 0
            var warCardsCount = 0
            for (war in warlog?.items!!) {
                for (warMember in war.participants!!) {
                    if (member?.tag.equals(warMember.tag)) {
                        warBattlesCount += warMember.battlesPlayed!!
                        warWinsCount += warMember.wins!!
                        warCardsCount += warMember.cardsEarned!!
                    }
                }
            }
            warBattles.text = warBattlesCount.toString()
            warWins.text = warWinsCount.toString()
            warCards.text = warCardsCount.toString()
            warWinPercentage.text = String.format("%.0f", ((warWinsCount * 100f) / warBattlesCount)) + "%"
        }

        private fun multiplyBasedOnDay(donationCount: Int?): Int? {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            var donationBasedOnDay = donationCount

            when (day) {
                Calendar.MONDAY -> {
                    donationBasedOnDay = (donationCount!! + 1) * 100
                }
                Calendar.TUESDAY -> {
                    donationBasedOnDay = donationCount!! * 6
                }
                Calendar.WEDNESDAY -> {
                    donationBasedOnDay = donationCount!! * 4
                }
                Calendar.THURSDAY -> {
                    donationBasedOnDay = donationCount!! * 2
                }
                Calendar.FRIDAY -> {

                }
                Calendar.SATURDAY -> {

                }
                Calendar.SUNDAY -> {

                }
            }

            return donationBasedOnDay
        }

    }


}