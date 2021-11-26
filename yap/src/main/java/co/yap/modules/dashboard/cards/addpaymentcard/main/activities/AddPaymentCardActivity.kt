package co.yap.modules.dashboard.cards.addpaymentcard.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.main.interfaces.IAddPaymentCard
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.spare.main.fragments.AddSpareCardFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardcolour.AddVirtualCardFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardcolour.AddVirtualCardFragmentDirections
import co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname.AddVirtualCardNameFragment
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import co.yap.yapcore.managers.FeatureProvisioning

class AddPaymentCardActivity : BaseBindingActivity<IAddPaymentCard.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AddPaymentCardActivity::class.java)
            return intent
        }

        var onBackPressCheck: Boolean = true
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_add_payment_cards

    override val viewModel: IAddPaymentCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddPaymentCardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@AddPaymentCardActivity, R.id.main_cards_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, singleClickObserver)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObserver(singleClickObserver)
        super.onDestroy()
    }

    private val singleClickObserver = Observer<Int> { id ->
        when (id) {
            R.id.tbBtnBack -> {
                onBackPressed()
            }
            R.id.ivInfo -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.main_cards_nav_host_fragment)
                fragment?.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let {fragment->
                        when(fragment){
                            is AddVirtualCardFragment ->
                                fragment.navigateToLanding()
                            is AddVirtualCardNameFragment ->
                                fragment.navigateToLanding()
                            is AddSpareCardFragment ->
                                fragment.navigateToLanding()
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_cards_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            if (onBackPressCheck) {
                super.onBackPressed()
            }

        }
//        if (!onBackPressCheck) {
//            return false
//        }
    }
     fun navigate(
        navDirection: NavDirections,
        fragment: AddVirtualCardFragment,
        screenType: FeatureSet = FeatureSet.NONE,
        navOptions: NavOptions? = androidx.navigation.navOptions {
            anim {
                enter = co.yap.yapcore.R.anim.slide_in_right
                exit = co.yap.yapcore.R.anim.slide_out_left
                popEnter = co.yap.yapcore.R.anim.slide_in_left
                popExit = co.yap.yapcore.R.anim.slide_out_right
            }
        }
    ) {
        if (FeatureProvisioning.getFeatureProvisioning(screenType)) {
            showBlockedFeatureAlert(this, screenType)
        } else {
           findNavController(fragment.id).navigate(navDirection, navOptions)
        }
    }
}