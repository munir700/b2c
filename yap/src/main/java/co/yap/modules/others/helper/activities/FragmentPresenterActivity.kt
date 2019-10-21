package co.yap.modules.others.helper.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.AppBarConfiguration
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityYapDashboardBinding
import co.yap.modules.dashboard.cards.status.fragments.YapCardStatusFragment
import co.yap.modules.dashboard.more.help.fragments.HelpSupportFragment
import co.yap.modules.others.helper.interfaces.IFragmentPresenter
import co.yap.modules.others.helper.viewmodels.FragmentPresenterViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants

class FragmentPresenterActivity : BaseBindingActivity<IFragmentPresenter.ViewModel>(),
    IFragmentPresenter.View,
    IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    companion object {
        const val key = "type"
        const val data = "payLoad"
        fun getIntent(context: Context, type: Int, payLoad: Parcelable?): Intent {
            val intent = Intent(context, FragmentPresenterActivity::class.java)
            intent.putExtra(key, type)
            if (payLoad != null)
                intent.putExtra(data, payLoad)
            return intent
        }
    }

    var modeCode: Int = 0

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_fragment_presenter

    override val viewModel: IFragmentPresenter.ViewModel
        get() = ViewModelProviders.of(this).get(FragmentPresenterViewModel::class.java)


    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

        if (intent != null && intent.extras != null && intent.hasExtra(key)) {
            modeCode = intent.getIntExtra(key, 0)
        }
        if (Constants.MODE_STATUS_SCREEN == modeCode) {
            if (intent.hasExtra(data)) { // because payload can be null
                val card = intent?.extras?.getParcelable<Card>(data)
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.container, YapCardStatusFragment())
                ft.commit()
            }
        } else {
            if (Constants.MODE_HELP_SUPPORT == modeCode) {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.container, HelpSupportFragment())
                ft.commit()
            }
        }
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
            }
        })
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getViewBinding(): ActivityYapDashboardBinding {
        return (viewDataBinding as ActivityYapDashboardBinding)
    }
}