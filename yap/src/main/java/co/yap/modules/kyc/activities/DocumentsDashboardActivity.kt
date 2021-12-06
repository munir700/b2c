package co.yap.modules.kyc.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IDocumentsDashboard
import co.yap.modules.kyc.viewmodels.DocumentsDashboardViewModel
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.deleteTempFolder
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.activity_documents_dashboard.*
import kotlinx.android.synthetic.main.layout_kyc_progress_toolbar.view.*
import java.io.File

class DocumentsDashboardActivity : BaseBindingActivity<IDocumentsDashboard.ViewModel>(), INavigator,
    IFragmentHolder {

    override val viewModel: IDocumentsDashboard.ViewModel
        get() = ViewModelProvider(this).get(DocumentsDashboardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.kyc_host_fragment)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_documents_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this should be only first time
        viewModel.name.value = intent.getValue(Constants.name, ExtraType.STRING.name) as? String
        viewModel.amendmentMap =
            intent.getSerializableExtra(Constants.KYC_AMENDMENT_MAP) as? HashMap<String?, List<String>?>
        viewModel.skipFirstScreen.value =
            intent.getValue(Constants.data, ExtraType.BOOLEAN.name) as? Boolean
        viewModel.showProgressBar.value = intent?.getBooleanExtra("GO_ERROR", true)
        viewModel.document =
            intent.getParcelableExtra("document") as? GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation
        if (viewModel.showProgressBar.value == false) {
            progressBar.visibility = View.GONE
        }
        addObserver()
    }

    private fun addObserver() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.finishKyc.observe(this, Observer {
            viewModel.paths.forEach { filePath ->
                File(filePath).deleteRecursively()
            }
            goToDashBoard(
                success = it.success,
                skippedPress = !it.success,
                status = it.status
            )
        })
        viewModel.showProgressBar.observe(this, Observer { showProgress ->
            if (showProgress) {
                progressBar.progressLay.visibility = View.VISIBLE
                progressBar.btnBack.visibility = View.GONE
            } else {
                progressBar.progressLay.visibility = View.GONE
                progressBar.btnBack.visibility = View.VISIBLE
            }
        })
        viewModel.hideProgressToolbar.observe(this, toolbarObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tbBtnBack, R.id.btnBack -> {
                onBackPressed()
            }
        }
    }

    private val toolbarObserver = Observer<Boolean> {
        progressBar.progressLay.visibility = if (it) View.GONE else View.VISIBLE
        progressBar.btnBack.visibility = if (it) View.GONE else View.VISIBLE
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.kyc_host_fragment)
        viewModel.skipFirstScreen.value?.let {
            if (it) {
                viewModel.paths.forEach { filePath ->
                    File(filePath).deleteRecursively()
                }
                super.onBackPressed()
            } else {
                if (!BackPressImpl(fragment).onBackPressed()) {
                    viewModel.paths.forEach { filePath ->
                        File(filePath).deleteRecursively()
                    }
                }
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        viewModel.paths.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
        context.deleteTempFolder()
        super.onDestroy()
    }

    private fun goToDashBoard(success: Boolean, skippedPress: Boolean, status: String = "") {
        val intent = Intent()
        intent.putExtra(Constants.result, success)
        intent.putExtra(Constants.skipped, skippedPress)
        intent.putExtra("status", status)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
