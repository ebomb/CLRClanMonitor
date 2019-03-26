package com.ebomb.clrclanmonitor.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.ebomb.clrclanmonitor.CLRConstants
import com.ebomb.clrclanmonitor.CLRConstants.CLAN_TAG
import com.ebomb.clrclanmonitor.R
import com.ebomb.clrclanmonitor.adapter.ClanMonitorAdapter
import com.ebomb.clrclanmonitor.model.Clan
import com.ebomb.clrclanmonitor.model.ClanMember
import com.ebomb.clrclanmonitor.model.Warlog
import com.ebomb.clrclanmonitor.network.ClanService
import com.ebomb.clrclanmonitor.network.PlayerService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class ClanActivity : AppCompatActivity() {

    @BindView(R.id.member_status)
    lateinit var memberStatusView: RecyclerView

    @BindView(R.id.progressBarBackground)
    lateinit var progressBar: FrameLayout

    var clanMonitorAdapter: ClanMonitorAdapter? = null
    var disposable: Disposable? = null

    val clanService by lazy {
        ClanService.create()
    }

    val playerService by lazy {
        PlayerService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clan)
        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.VISIBLE
        disposable = clanService.clanInfo(CLAN_TAG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { clan -> showClanResults(clan) },
                        { error -> showError(error.message) }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable = null
    }

    private fun showClanResults(clan: Clan?) {
        val memberList: List<ClanMember>? = clan?.memberList
        Collections.sort(memberList, DonationComparator())
        disposable = clanService.warlog(CLAN_TAG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { warlog -> handleWarlog(memberList, warlog) },
                        { error -> showError(error.message) }
                )
    }

    private fun handleWarlog(memberList: List<ClanMember>?, warlog: Warlog?) {
        if (memberList != null) {
            for (member in memberList) {
                disposable = playerService.battlelog(member.tag!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { playerBattle ->
                                    for (team in playerBattle.team!!) {
                                        if (!team.clan?.tag.equals(CLRConstants.CLAN_TAG)) {
                                            member.recentPlayer = true
                                        }
                                    }
                                },
                                { error ->
                                    Log.e("Error", error.message)
                                }
                        )
            }

            clanMonitorAdapter = ClanMonitorAdapter(memberList, warlog)
            memberStatusView.layoutManager = LinearLayoutManager(this)
            memberStatusView.adapter = clanMonitorAdapter
            progressBar.visibility = View.GONE
        } else {
            showError("No players found!")
        }
    }

    private fun showError(message: String?) {
        Log.e("Error", message)
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    class DonationComparator : Comparator<ClanMember> {
        override fun compare(member1: ClanMember, member2: ClanMember): Int {
            val firstHomeScreenCardPriority = member1.donations
            val secondHomeScreenCardPriority = member2.donations
            return secondHomeScreenCardPriority!! - firstHomeScreenCardPriority!!
        }
    }
}
