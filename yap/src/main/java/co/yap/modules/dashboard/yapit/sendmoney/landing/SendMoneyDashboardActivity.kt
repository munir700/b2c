package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivitySendMoneyDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.homecountry.SMHomeCountryActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyDashboardViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyDashboard
import co.yap.networking.customers.requestdtos.Contact
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.widgets.scanqrcode.ScanQRCodeFragment
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.interfaces.OnItemClickListener


class SendMoneyDashboardActivity : BaseBindingActivity<ISendMoneyDashboard.ViewModel>(),
    ISendMoneyDashboard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_dashboard
    var permissionHelper: PermissionHelper? = null
    override val viewModel: SendMoneyDashboardViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyDashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setupRecycleView()
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
                showToast("Process under working")
            }
            sendMoneyToLocalBank -> {
                startSendMoneyFlow(SendMoneyTransferType.LOCAL.name)
            }
            sendMoneyToInternational -> {
                startSendMoneyFlow(SendMoneyTransferType.INTERNATIONAL.name)
            }
            sendMoneyToHomeCountry -> {
                launchActivity<SMHomeCountryActivity> { }
            }
            sendMoneyQRCode -> {
                checkPermission()
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

    private fun startQrFragment() {
        startFragmentForResult<ScanQRCodeFragment>(ScanQRCodeFragment::class.java.name) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val contact = data?.getParcelableExtra<Contact>("contact")
            }
        }
    }

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                startQrFragment()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                if (grantedPermission.contains(Manifest.permission.CAMERA))
                    startQrFragment()
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDeniedBySystem() {
                showToast("Can't proceed without permissions")

            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>, grantResults
        )
    }
}
