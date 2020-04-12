package co.yap.yapcore.dagger.base.interfaces

/**
 * Created by duypham on 9/10/17.
 * Indicate refreshable ui objects (be able to swipe to refresh), eg. activity, fragment...
 */

interface UiRefreshable : OnPullToRefreshable {
    fun doneRefresh(isCompleted:Boolean)
    fun refreshWithUi()
    fun refreshWithUi(delay: Int)
    fun setRefreshEnabled(enabled: Boolean)
}
