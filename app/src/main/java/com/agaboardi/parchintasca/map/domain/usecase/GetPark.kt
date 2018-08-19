package com.agaboardi.parchintasca.map.domain.usecase

import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.data.source.MainDataSource
import com.agaboardi.parchintasca.data.source.MainRepository
import com.agaboardi.parchintasca.data.source.local.db.models.Park

class GetPark(
        val mainRepository: MainRepository
): UseCase<GetPark.RequestValues, GetPark.ResponseValue>() {
    override fun executeUseCase(requestValues: RequestValues?) {
        val park = requestValues?.parkId

        park?.let {
            mainRepository.getPark(it, object: MainDataSource.GetParkCallback{
                override fun onParkLoaded(park: Park) {
                    useCaseCallback?.onSuccess(ResponseValue(park))
                }

                override fun onDataNotAvailable() {
                    useCaseCallback?.onError()
                }
            })
        } ?: useCaseCallback?.onError()
    }

    class RequestValues(val parkId: Int): UseCase.RequestValues
    class ResponseValue(val park: Park): UseCase.ResponseValue
}