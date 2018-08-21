package com.ebomb.clrclanmonitor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ebomb.clrclanmonitor.model.Clan
import com.ebomb.clrclanmonitor.network.ClanService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ClanActivity : AppCompatActivity() {

    private val clanTag = "%23P8PJLP8P"
    val clanService by lazy {
        ClanService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clan)
    }

    override fun onResume() {
        super.onResume()
        disposable = clanService.clanInfo(clanTag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> showResult(result) },
                        { error -> showError(error.message) }
                )
    }

    private fun showResult(result: Clan?) {
        Toast.makeText(this, "Donations = " + result?.memberList?.get(0)?.donations, Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable = null
    }
}
