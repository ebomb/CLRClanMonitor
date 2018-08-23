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
            status.text = "INACTIVE!!!";
            donations.text = member?.donations.toString()

        }

    }


}