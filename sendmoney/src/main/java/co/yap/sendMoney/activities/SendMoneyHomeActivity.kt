package co.yap.sendMoney.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.sendMoney.R
import co.yap.sendMoney.interfaces.ISendMoney
import co.yap.sendMoney.viewmodels.SendMoneyViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class SendMoneyHomeActivity : BaseBindingActivity<ISendMoney.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SendMoneyHomeActivity::class.java)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_send_money_home

    override val viewModel: ISendMoney.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@SendMoneyHomeActivity, R.id.send_money_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, backButtonObserver)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private val backButtonObserver = Observer<Int> { onBackPressed() }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.send_money_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            when (requestCode) {
                RequestCodes.REQUEST_TRANSFER_MONEY -> {
                    if (resultCode == Activity.RESULT_OK) {
                        if (data.getBooleanExtra(Constants.MONEY_TRANSFERED, false)) {
                            val intent = Intent()
                            intent.putExtra(Constants.MONEY_TRANSFERED, true)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else if (data.getBooleanExtra(Constants.BENEFICIARY_CHANGE, false)) {
                            val intent = Intent()
                            intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}