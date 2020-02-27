package co.yap.modules.dashboard.yapit.sendmoney.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.BeneficiaryCashTransferViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.getSnackBarFromQueue
import co.yap.yapcore.helpers.showSnackBar
import co.yap.yapcore.helpers.updateSnackBarText
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_beneficiary_cash_transfer.*


class BeneficiaryCashTransferActivity : BaseBindingActivity<IBeneficiaryCashTransfer.ViewModel>(),
    IFragmentHolder, INavigator {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_beneficiary_cash_transfer

    override val viewModel: IBeneficiaryCashTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryCashTransferViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@BeneficiaryCashTransferActivity,
            R.id.beneficiary_cash_transfer_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBeneficiary()
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, errorEvent)
    }

    val errorEvent = Observer<String> {
        if (!it.isNullOrEmpty())
            showErrorSnackBar(it)
        else
            hideErrorSnackBar()
    }
    private fun showErrorSnackBar(errorMessage: String) {
        getSnackBarFromQueue(0)?.let {
            if (it.isShown) {
                it.updateSnackBarText(errorMessage)
            }
        } ?: clFTSnackbar.showSnackBar(
            msg = errorMessage,
            viewBgColor = R.color.errorLightBackground,
            colorOfMessage = R.color.error, duration = Snackbar.LENGTH_INDEFINITE
        )
    }

    private fun hideErrorSnackBar() {
        cancelAllSnackBar()
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> onBackPressed()
            R.id.tvRightToolbar -> {
                val i = Intent()
                intent?.let { inten ->
                    i.putExtra(
                        Constants.BENEFICIARY_CHANGE,
                        inten.getBooleanExtra(Constants.IS_NEW_BENEFICIARY, false)
                    )
                    setResult(Activity.RESULT_OK, i)
                    finish()
                }
            }
        }
    }

    private fun getBeneficiary() {
        if (intent != null) {
            viewModel.state.beneficiary =
                intent.getParcelableExtra(Constants.BENEFICIARY) as? Beneficiary?

            viewModel.state.position = intent.getIntExtra(Constants.POSITION, 0)
        }
        // return intent.getSerializableExtra(ACCOUNT_TYPE) as AccountType
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.beneficiary_cash_transfer_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.errorEvent.removeObservers(this)
        super.onDestroy()
    }

}