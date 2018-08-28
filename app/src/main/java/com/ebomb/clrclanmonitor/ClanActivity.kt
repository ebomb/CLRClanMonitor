package com.ebomb.clrclanmonitor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.ebomb.clrclanmonitor.adapter.NonActiveMemberAdapter
import com.ebomb.clrclanmonitor.model.Clan
import com.ebomb.clrclanmonitor.model.ClanMember
import com.ebomb.clrclanmonitor.model.Warlog
import com.ebomb.clrclanmonitor.network.ClanService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class ClanActivity : AppCompatActivity() {

    @BindView(R.id.non_active_members)
    lateinit var nonActiveMembers: RecyclerView

    var nonActiveMemberAdapter: NonActiveMemberAdapter? = null
    var disposable: Disposable? = null
    private val clanTag = "#P8PJLP8P"

    val clanService by lazy {
        ClanService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clan)
        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        disposable = clanService.clanInfo(clanTag)
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
        disposable = clanService.warlog(clanTag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { warlog -> handleWarlog(memberList, warlog) },
                        { error -> showError(error.message) }
                )
    }

    private fun handleWarlog(memberList: List<ClanMember>?, warlog: Warlog?) {
        nonActiveMemberAdapter = NonActiveMemberAdapter(memberList, warlog)
        nonActiveMembers.layoutManager = LinearLayoutManager(this)
        nonActiveMembers.adapter = nonActiveMemberAdapter
    }

    private fun showError(message: String?) {
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
