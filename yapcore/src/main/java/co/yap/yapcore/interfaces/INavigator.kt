package co.yap.yapcore.interfaces

import co.yap.yapcore.helpers.Navigator

interface INavigator {
    val navigator: Navigator
    /**
     * get the id of navigation host fragment
     */
    val navHostId: Int
    get() = 0

}