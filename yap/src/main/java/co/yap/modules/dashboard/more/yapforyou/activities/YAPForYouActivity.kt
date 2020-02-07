package co.yap.modules.dashboard.more.yapforyou.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.interfaces.IMore
import co.yap.modules.dashboard.more.main.viewmodels.MoreViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class YAPForYouActivity : BaseBindingActivity<IMore.ViewModel>(), INavigator,
    IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_yap_for_you

    override val viewModel: IMore.ViewModel
        get() = ViewModelProviders.of(this).get(MoreViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@YAPForYouActivity, R.id.yap_for_you_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backButtonPressEvent.observe(this, backButtonObserver)

    }

    override fun onDestroy() {
        viewModel.backButtonPressEvent.removeObservers(this)
        viewModel.preventTakeDeviceScreenShot.removeObservers(this)
        super.onDestroy()
    }

    private val backButtonObserver = Observer<Boolean> { onBackPressed() }


    fun getIntentData(): Boolean {
        if (intent != null) {
            if (intent.hasExtra("isDrawerNav"))
                return intent.getBooleanExtra("isDrawerNav", false)
        }
        return false
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.yap_for_you_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }

}