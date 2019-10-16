package co.yap.modules.dashboard.more.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.interfaces.IMore
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment.Companion.checkMore
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment.Companion.checkScanned
import co.yap.modules.dashboard.more.viewmodels.MoreViewModel
import co.yap.modules.kyc.activities.DocumentsDashboardActivity.Companion.hasStartedScanner
import co.yap.modules.kyc.activities.DocumentsDashboardActivity.Companion.isFromMoreSection
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

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MoreActivity::class.java)
            return intent
        }
        var isDocumentRequired : Boolean =false

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
         checkMore = false
         checkScanned = false
      isFromMoreSection  = false
       hasStartedScanner = false
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }

    fun hideToolbar() {
        toolbar.visibility = View.INVISIBLE
    }

    fun goneToolbar() {
        toolbar.visibility = View.GONE
    }

    fun visibleToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_more_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }

}