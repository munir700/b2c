package co.yap.modules.dashboard.cards.addpaymentcard.main.fragments

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase
import co.yap.yapcore.R
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.managers.FeatureProvisioning


abstract class AddPaymentChildFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is AddPaymentChildViewModel<*>) {
            (viewModel as AddPaymentChildViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(AddPaymentCardViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    protected fun navigateToNext(
        navDirection: NavDirections,
        screenType: FeatureSet = FeatureSet.NONE,
        navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
            }
        }
    ) {
        if (FeatureProvisioning.getFeatureProvisioning(screenType)) {
            showBlockedFeatureAlert(requireActivity(), screenType)
        } else {
            findNavController().navigate(navDirection, navOptions)
        }
    }

    fun navigateToBack(
        destinationId: NavDirections,
        @IdRes popupTo: Int,
        screenType: FeatureSet = FeatureSet.NONE
        ) {
        if (FeatureProvisioning.getFeatureProvisioning(screenType)) {
            showBlockedFeatureAlert(requireActivity(), screenType)
        } else {
            findNavController().navigate(
                destinationId,
                navOptions {
                    popUpTo(popupTo) {
                        inclusive = true
                    }
                    anim {
                        enter = R.anim.slide_in_left
                        exit = R.anim.slide_out_right
                    }
                })
        }
    }
}