package co.yap.modules.kyc.activities

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
import com.digitify.identityscanner.modules.docscanner.activities.IdentityScannerActivity

class DocumentsDashboardActivity : BaseBindingActivity<IDocumentsDashboard.ViewModel>(), INavigator,
    IFragmentHolder {

    companion object {
        var isFromMoreSection: Boolean = false
        var hasStartedScanner: Boolean = false

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
        return intent.getStringExtra(getString(R.string.arg_name))
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isFromMoreSection) {
            IdentityScannerActivity.CLOSE_SCANNER = false
        }
    }


}