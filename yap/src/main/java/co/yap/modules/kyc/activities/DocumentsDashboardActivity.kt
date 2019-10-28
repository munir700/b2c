package co.yap.modules.kyc.activities

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
        var isFromMoreSection: Boolean = false
        var hasStartedScanner: Boolean = false

        const val key = "name"
        const val data = "payLoad"
        fun getIntent(context: Context, name: String, isFromMoreSection: Boolean): Intent {
            val intent = Intent(context, DocumentsDashboardActivity::class.java)
            intent.putExtra(key, name)
            intent.putExtra(data, isFromMoreSection)
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
        if (isFromMoreSection) {
            super.onBackPressed()
        } else {
            if (!BackPressImpl(fragment).onBackPressed()) {
                super.onBackPressed()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.name = getBundledName()
        isFromMoreSection = intent.getBooleanExtra("isFromMoreSection", false)
    }

    private fun getBundledName(): String {
        return if (intent.hasExtra("name"))
            intent.getStringExtra("name")
        else ""
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFromMoreSection) {
            //todo need to verify that code
            //IdentityScannerActivity.CLOSE_SCANNER = false
        }
    }

}