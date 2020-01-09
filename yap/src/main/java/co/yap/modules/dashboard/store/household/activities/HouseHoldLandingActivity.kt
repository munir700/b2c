package co.yap.modules.dashboard.store.household.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldLanding
import co.yap.modules.dashboard.store.household.viewmodels.HouseHoldLandingViewModel
import co.yap.yapcore.BaseBindingActivity

class HouseHoldLandingActivity : BaseBindingActivity<IHouseHoldLanding.ViewModel>(),
    IHouseHoldLanding.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_house_hold_landing

    override val viewModel: IHouseHoldLanding.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldLandingViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HouseHoldLandingActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.btnGetHouseHoldAccount -> {
                    startActivity(SubscriptionSelectionActivity.newIntent(this))
                }

                R.id.imgClose -> {
                    finish()
                }

            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)

    }
}