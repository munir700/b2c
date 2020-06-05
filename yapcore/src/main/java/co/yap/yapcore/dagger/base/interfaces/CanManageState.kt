package co.yap.yapcore.dagger.base.interfaces

import android.os.Bundle

/**
 * A marker interface used to indicate the support for the management of the state.
 */
interface CanManageState {

    /**
     * Gets called when the actual process of the state restoring can be performed.
     *
     * @param bundle the state-containing bundle
     */
    fun onRestoreState(bundle: Bundle)

    /**
     * Gets called when tha actual process of the state saving can be performed.
     *
     * @param bundle the bundle to save the state into
     */
    fun onSaveState(bundle: Bundle)

}