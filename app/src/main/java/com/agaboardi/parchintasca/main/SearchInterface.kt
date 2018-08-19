package com.agaboardi.parchintasca.main

interface SearchInterface {
    fun onSearchViewClosed()
    fun onSearchViewShown()
    fun onQueryTextSubmit(query: String?): Boolean
    fun onQueryTextChange(newText: String?)
}