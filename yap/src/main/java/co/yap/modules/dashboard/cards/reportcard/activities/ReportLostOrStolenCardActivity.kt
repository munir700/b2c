package co.yap.modules.dashboard.cards.reportcard.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.reportcard.interfaces.IReportOrLostBase
import co.yap.modules.dashboard.cards.reportcard.viewmodels.ReportLostOrStolenCardBaseViewModels
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class ReportLostOrStolenCardActivity : BaseBindingActivity<IReportOrLostBase.ViewModel>(),
    INavigator,
    IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_report_or_stolen_cards

    override val viewModel: IReportOrLostBase.ViewModel
        get() = ViewModelProviders.of(this).get(ReportLostOrStolenCardBaseViewModels::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@ReportLostOrStolenCardActivity,
            R.id.main_report_stolen_cards_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_report_stolen_cards_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}