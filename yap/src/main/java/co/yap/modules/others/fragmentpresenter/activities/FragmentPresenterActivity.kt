package co.yap.modules.others.fragmentpresenter.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityFragmentPresenterBinding
import co.yap.modules.dashboard.cards.status.fragments.YapCardStatusFragment
import co.yap.modules.dashboard.more.help.fragments.HelpSupportFragment
import co.yap.modules.others.fragmentpresenter.interfaces.IFragmentPresenter
import co.yap.modules.others.fragmentpresenter.viewmodels.FragmentPresenterViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.replaceFragment

class FragmentPresenterActivity : BaseBindingActivity<IFragmentPresenter.ViewModel>(),
    IFragmentPresenter.View,
    IFragmentHolder {

    companion object {
        const val key = "txnType"
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null && intent.extras != null && intent.hasExtra(key)) {
            modeCode = intent.getIntExtra(key, 0)
        }
        if (Constants.MODE_STATUS_SCREEN == modeCode) {
            if (intent.hasExtra(data)) { // because payload can be null
                val card = intent?.extras?.getParcelable<Card>(data)
                replaceFragment(
                    localClassName,
                    R.id.container,
                    YapCardStatusFragment.newInstance(card)
                )

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

    fun getViewBinding(): ActivityFragmentPresenterBinding {
        return (viewDataBinding as ActivityFragmentPresenterBinding)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if (supportFragmentManager.findFragmentByTag(localClassName) != null) {
//            val trans = supportFragmentManager.beginTransaction()
//            trans.remove(supportFragmentManager.findFragmentByTag(localClassName)!!)
//            trans.commit()
//            supportFragmentManager.popBackStack()
//        }
    }
}