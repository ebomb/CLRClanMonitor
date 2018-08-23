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
import java.util.*

class NonActiveMemberAdapter(private var clanMembers: List<ClanMember>?) : RecyclerView.Adapter<NonActiveMemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonActiveMemberAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val searchResultView = inflater.inflate(R.layout.view_non_active_member, parent, false)
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

        @BindView(R.id.status)
        lateinit var status: TextView

        @BindView(R.id.donations)
        lateinit var donations: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(position: Int) {
            val member = clanMembers?.get(position)
            name.text = member?.name.toString()

            var donationCount = member?.donations
            donations.text = donationCount.toString()

            var statusText = "KICK!"
            var color = itemView.resources.getColor(R.color.red, null)

            donationCount = multiplyBasedOnDay(donationCount)
            if (donationCount != null) {
                when {
                    donationCount > 750 -> {
                        statusText = "Extremely Active"
                        color = itemView.resources.getColor(R.color.green_dark, null)
                    }
                    donationCount > 500 -> {
                        statusText = "Very Active"
                        color = itemView.resources.getColor(R.color.green, null)
                    }
                    donationCount > 250 -> {
                        statusText = "Active"
                        color = itemView.resources.getColor(R.color.green, null)
                    }
                    donationCount > 100 -> {
                        statusText = "Lightly Active"
                        color = itemView.resources.getColor(R.color.orange, null)

                    }
                    donationCount == 0 -> {
                        statusText = "KICK!"
                    }
                }
            }
            status.text = statusText
            status.setTextColor(color)

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
                    donationBasedOnDay = donationCount!! * 50
                }
                Calendar.WEDNESDAY -> {
                    donationBasedOnDay = donationCount!! * 10
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