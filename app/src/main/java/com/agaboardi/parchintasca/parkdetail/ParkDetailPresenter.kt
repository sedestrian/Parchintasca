package com.agaboardi.parchintasca.parkdetail

import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.common.extensions.toCalendar
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.usecase.GetPark
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkSeason
import mindtek.it.bikeapp.kotlin.extensions.safeLet
import java.util.*

class ParkDetailPresenter(
        val parkId: Int,
        val view: ParkDetailContract.View,
        val useCaseHandler: UseCaseHandler,
        val getPark: GetPark
) : ParkDetailContract.Presenter {
    private val regexSummer = """(primavera.estate: )(\d+,\d\d) - (\d{1,2},\d{2})""".toRegex()
    private val regexWinter = """(autunno.inverno: )(\d+,\d\d) - (\d{1,2},\d{2})""".toRegex()

    init {
        view.setPresenter(this)
    }

    override fun start() {
        view.setupTags()
        view.setupRecycler()
        loadPark(false)
    }

    override fun loadPark(forceUpdate: Boolean) {
        loadPark(forceUpdate, true)
    }

    override fun loadPark(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true)
        }

        val requestValues = GetPark.RequestValues(parkId)

        useCaseHandler.execute(getPark, requestValues,
                object : UseCase.UseCaseCallback<GetPark.ResponseValue> {
                    override fun onSuccess(response: GetPark.ResponseValue) {
                        if(showLoadingUI){
                            view.setLoadingIndicator(false)
                        }
                        val park = response.park
                        handlePark(park)
                    }

                    override fun onError() {
                        view.showError()
                    }
                }
        )
    }

    private fun handlePark(park: Park){
        view.showPark(park)
        calculateDates(park)
    }

    private fun calculateDates(park: Park){
        val summer = getSummerHours(park)
        val winter = getWinterHours(park)
        val season = getSeason()
        safeLet(summer, winter){ summer, winter ->
            view.showHours(summer, winter, season)
        } ?: view.showError()
    }

    private fun getSummerHours(park: Park): ParkSeason? {
        park.opening_time?.let {
            val result = regexSummer.find(it, 0)
            result?.let {
                val full = result.groupValues[0]
                val season = result.groupValues[1]
                val opening = result.groupValues[2]
                val closing = result.groupValues[3]
                return ParkSeason(season, opening, closing)
            }
        }
        return null
    }

    private fun getWinterHours(park: Park): ParkSeason? {
        park.opening_time?.let {
            val result = regexWinter.find(it, 0)
            result?.let {
                val full = result.groupValues[0]
                val season = result.groupValues[1]
                val opening = result.groupValues[2]
                val closing = result.groupValues[3]
                return ParkSeason(season, opening, closing)
            }
        }
        return null
    }

    private fun getSeason(): Int{
        val today = Calendar.getInstance()
        val summerStart = "21 Jun".toCalendar()
        val autumnStart = "23 Sep".toCalendar()
        val winterStart = "21 Dec".toCalendar()
        val springStart = "20 Mar".toCalendar()
        return if(today.after(summerStart) && today.before(autumnStart))
            SUMMER
        else if(today.after(autumnStart) && today.before(winterStart))
            AUTUMN
        else if(today.after(winterStart) && today.before(springStart))
            WINTER
        else
            SPRING
    }

    companion object {
        val SPRING = 0
        val SUMMER = 0
        val AUTUMN = 1
        val WINTER = 1
    }
}