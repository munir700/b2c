package co.yap.yapcore.hilt.base

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras
import co.yap.yapcore.helpers.extentions.handleExtras

/**
 * Created by Safi ur Rehman
 */
open class MvvmNavHostFragmentV2 : NavHostFragment(), CanFetchExtras {

//    @Inject
//    protected lateinit var daggerFragmentInjectionFactory: InjectingFragmentFactory

    override fun fetchExtras(extras: Bundle?) {
        childFragmentManager.fragments.handleExtras(extras)
    }
}