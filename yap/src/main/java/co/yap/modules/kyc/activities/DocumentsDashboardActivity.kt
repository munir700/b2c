package co.yap.modules.kyc.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.viewmodels.DocumentsDashboardViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class DocumentsDashboardActivity : BaseBindingActivity<IDocumentsDashboard.ViewModel>(), INavigator,
    IFragmentHolder {

    override val viewModel: IDocumentsDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DocumentsDashboardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.kyc_host_fragment)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_documents_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.name.value = intent.getValue(Constants.name, ExtraType.STRING.name) as? String
        viewModel.skipFirstScreen.value =
            intent.getValue(Constants.data, ExtraType.BOOLEAN.name) as? Boolean
        addObserver()
    }

    private fun addObserver() {
        viewModel.finishKyc.observe(this, Observer {
            if (it.success)
                goToDashBoard(
                    success = true,
                    skippedPress = false
                )
        })
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.kyc_host_fragment)
        viewModel.skipFirstScreen.value?.let {
            if (it) {
                super.onBackPressed()
            } else {
                if (!BackPressImpl(fragment).onBackPressed()) {
                    super.onBackPressed()
                }
            }
        }
    }

    fun goToDashBoard(success: Boolean, skippedPress: Boolean, error: Boolean = false) {
        val intent = Intent()
        intent.putExtra(Constants.result, success)
        intent.putExtra(Constants.skipped, skippedPress)
        intent.putExtra("error", error)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
