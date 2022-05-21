package co.yap.widgets.search

interface IYapSearchView {
    fun onSearchActive(isActive:Boolean)
    fun onTypingSearch(search: String?)
}