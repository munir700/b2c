package co.yap.modules.dashboard.yapit.topup.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.main.interfaces.IY2Y
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class TopUpLandingActivity : BaseBindingActivity<IY2Y.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        const val searching = "searching"
        const val data = "payLoad"
        fun getIntent(context: Context, isSearching: Boolean, payLoad: Parcelable?): Intent {
            val intent = Intent(context, TopUpLandingActivity::class.java)
            if (payLoad != null)
                intent.putExtra(data, payLoad)
            intent.putExtra(searching, isSearching)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_landing
    override val viewModel: IY2Y.ViewModel get() = ViewModelProviders.of(this).get(Y2YViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@TopUpLandingActivity,
            R.id.main_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isSearching.value = intent.getBooleanExtra(searching, false)
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> {
                onBackPressed()
            }
            R.id.tbIvGift -> {
                Utils.shareText(this, getBody())
            }
        }
    }

    private fun getBody(): String {
        return Utils.getGeneralInvitationBody(this)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null) {
                hideKeyboard()
            }
        }

        return super.dispatchTouchEvent(event)
    }
}