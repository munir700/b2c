package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivitySendMoneyDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.homecountry.SMHomeCountryActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyDashboardViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyDashboard
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.networking.customers.requestdtos.Contact
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.interfaces.OnItemClickListener


class SendMoneyDashboardActivity : BaseBindingActivity<ISendMoneyDashboard.ViewModel>(),
    ISendMoneyDashboard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_dashboard
    override var permissionHelper: PermissionHelper? = null

    override val viewModel: SendMoneyDashboardViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyDashboardViewModel::class.java)
    val vs: ViewStub by lazy {
        findViewById<ViewStub>(R.id.vsRecentBeneficiaries)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewStub()
        setObservers()
        setupRecycleView()
    }

    private fun initViewStub() {
        vs.layoutResource = R.layout.layout_recent_beneficiaries_recylcerview
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private fun setupRecycleView() {
        getBinding().recyclerOptions.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large) ?: 16, 2, true
            )
        )
        viewModel.dashboardAdapter.allowFullItemClickListener = true
        viewModel.dashboardAdapter.setItemListener(itemClickListener)
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.clickEvent.setValue(pos)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            sendMoneyToYAPContacts -> {
                checkPermission()
            }
            sendMoneyToLocalBank -> {
                startSendMoneyFlow(SendMoneyTransferType.LOCAL.name)
            }
            sendMoneyToInternational -> {
                startSendMoneyFlow(SendMoneyTransferType.INTERNATIONAL.name)
            }
            sendMoneyToHomeCountry -> {
                launchActivity<SMHomeCountryActivity>()
            }
            sendMoneyQRCode -> {
                startFragmentForResult<ScanQRCodeFragment>(ScanQRCodeFragment::class.java.name) { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        val contact = data?.getParcelableExtra<Contact>("contact")
                    }
                }
            }
            R.id.tvrecentTransfer, R.id.hiderecentext -> {
                viewModel.state.isRecentsVisible.set(getBinding().hiderecentext.visibility == View.VISIBLE)
                vs.visibility =
                    if (getBinding().hiderecentext.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
    }

    private fun startSendMoneyFlow(sendMoneyType: String) {
        launchActivity<SendMoneyLandingActivity> {
            putExtra(
                SendMoneyLandingActivity.TransferType,
                sendMoneyType
            )
            putExtra(SendMoneyLandingActivity.searching, false)
        }
    }

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.READ_CONTACTS
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openY2YScreen()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                openY2YScreen()
            }

            override fun onPermissionDenied() {
                openY2YScreen()
            }

            override fun onPermissionDeniedBySystem() {
                openY2YScreen()
            }
        })
    }

    private fun openY2YScreen() {
        launchActivity<YapToYapDashboardActivity>(type = FeatureSet.YAP_TO_YAP) {
            putExtra(YapToYapDashboardActivity.searching, false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionHelper != null) {
            permissionHelper?.onRequestPermissionsResult(
                requestCode,
                permissions as Array<String>,
                grantResults
            )
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun getBinding(): ActivitySendMoneyDashboardBinding {
        return viewDataBinding as ActivitySendMoneyDashboardBinding
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
