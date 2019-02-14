package com.agaboardi.parchintasca.main

interface SearchContract {
    interface Host{
        fun addSubscriber(subscriber: Subscriber)
    }

    interface Subscriber: SearchInterface, FilterInterface{

    }
}