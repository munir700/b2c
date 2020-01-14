package co.yap.modules.kyc.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.viewmodels.DocumentsDashboardViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class DocumentsDashboardActivity : BaseBindingActivity<IDocumentsDashboard.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        const val name = "name"
        const val data = "payLoad"
        const val result = "result"
        const val skipped = "skipped"
        fun getIntent(context: Context, customerName: String?, allowSkip: Boolean): Intent {
            val intent = Intent(context, DocumentsDashboardActivity::class.java)
            intent.putExtra(name, customerName)
            intent.putExtra(data, allowSkip)
            return intent
        }
    }

    override val viewModel: IDocumentsDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DocumentsDashboardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.kyc_host_fragment)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_documents_dashboard

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.kyc_host_fragment)
        viewModel.allowSkip.value?.let {
            if (it) {
                super.onBackPressed()
            } else {
                if (!BackPressImpl(fragment).onBackPressed()) {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.name.value = getBundledName()
        viewModel.allowSkip.value = intent.getBooleanExtra(data, false)
    }

    private fun getBundledName(): String? {
        return if (intent.hasExtra(name))
            intent.getStringExtra(name)
        else null
    }

    fun goToDashBoard(success: Boolean, skippedPress: Boolean) {
        val intent = Intent()
        intent.putExtra(result, success)
        intent.putExtra(skipped, skippedPress)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}