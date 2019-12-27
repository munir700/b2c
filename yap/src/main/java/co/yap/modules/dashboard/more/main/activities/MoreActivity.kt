package co.yap.modules.dashboard.more.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.interfaces.IMore
import co.yap.modules.dashboard.more.main.viewmodels.MoreViewModel
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.activity_add_payment_cards.*

class MoreActivity : BaseBindingActivity<IMore.ViewModel>(), INavigator,
    IFragmentHolder {

    public companion object {
        // do not remove this boolean variable
        var navigationVariable: Boolean = false

        fun newIntent(context: Context, isDrawerNav: Boolean = false): Intent {
            val intent = Intent(context, MoreActivity::class.java)
            intent.putExtra("isDrawerNav", isDrawerNav)
            return intent
        }

        var isDocumentRequired: Boolean = false
        var showExpiredIcon: Boolean = false

    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_more

    override val viewModel: IMore.ViewModel
        get() = ViewModelProviders.of(this).get(MoreViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@MoreActivity, R.id.main_more_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        super.onDestroy()
        PersonalDetailsFragment.checkMore = false
        PersonalDetailsFragment.checkScanned = false
        DocumentsDashboardActivity.isFromMoreSection = false
        DocumentsDashboardActivity.hasStartedScanner = false
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    public fun hideToolbar() {
        toolbar.visibility = View.INVISIBLE
    }

    public fun goneToolbar() {
        toolbar.visibility = View.GONE
    }

    fun visibleToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    fun getIntentData(): Boolean {
        if (intent != null) {
            if(intent.hasExtra("isDrawerNav"))
            return intent.getBooleanExtra("isDrawerNav", false)
        }
        return false
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_more_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }

}