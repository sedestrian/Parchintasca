package com.agaboardi.parchintasca.map.domain.usecase

import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.data.source.MainDataSource
import com.agaboardi.parchintasca.data.source.MainRepository
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.filter.ParkFilterFactory
import com.agaboardi.parchintasca.map.domain.filter.ParkFilterType
import com.agaboardi.parchintasca.map.domain.filter.ParksFilter
import com.agaboardi.parchintasca.map.domain.model.ParkCategory

class GetParks constructor(
        private val mainRepository: MainRepository,
        private val filterFactory: ParkFilterFactory
): UseCase<GetParks.RequestValues, GetParks.ResponseValue>() {
    override fun executeUseCase(requestValues: RequestValues?) {
        mainRepository.loadParks(object: MainDataSource.ParksLoadedCallback{
            override fun onParksLoaded(parks: List<Park>) {
                val currentFilter = requestValues?.currentFilter
                val query = requestValues?.currentQuery
                val types = requestValues?.filterTypes
                val parksFilter = filterFactory.create(currentFilter)

                val parksFiltered = parksFilter?.filter(types, parks, query)
                val responseValue = ResponseValue(parksFiltered ?: listOf())
                useCaseCallback?.onSuccess(responseValue)
            }

            override fun onDataNotAvailable() {
                useCaseCallback?.onError()
            }
        })
    }

    class RequestValues(
            val forceUpdate: Boolean,
            val filterTypes: List<ParkCategory>,
            val currentFilter: ParkFilterType? = null,
            val currentQuery: String? = null): UseCase.RequestValues
    class ResponseValue(val parks: List<Park>): UseCase.ResponseValue
}