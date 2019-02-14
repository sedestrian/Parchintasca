package com.agaboardi.parchintasca.common.base

interface BaseView<T: BasePresenter> {
    fun setPresenter(presenter: T)
}