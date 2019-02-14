package com.agaboardi.parchintasca.parkdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.extensions.replaceFragment
import com.agaboardi.parchintasca.common.injection.Injection

class ActivityParkDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_park_detail)

        val id = intent.getIntExtra(PARK_ID, -1)
        val fragment = ParkDetailFragment.newInstance()

        ParkDetailPresenter(
                id,
                fragment,
                Injection.provideUseCaseHandler(),
                Injection.provideGetPark()
        )

        replaceFragment(fragment, R.id.container)
    }

    companion object {
        const val PARK_ID = "activity_park_detail_park_id"
    }
}
