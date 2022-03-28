package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.databinding.ActivitySendMoneyLinearDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.homecountry.SMHomeCountryActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyLinearDashboardViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyLinearDashboard
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyCategoryType
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLinearOptions
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.home.main.SMBeneficiaryParentActivity
import co.yap.sendmoney.y2y.home.activities.YapToYapDashboardActivity
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.widgets.scanqrcode.ScanQRCodeFragment
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

class SendMoneyLinearDashboardActivity : BaseBindingActivity<ISendMoneyLinearDashboard.ViewModel>(),
    ISendMoneyLinearDashboard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_linear_dashboard
    override var permissionHelper: PermissionHelper? = null
    override val viewModel: SendMoneyLinearDashboardViewModel
        get() = ViewModelProvider(this).get(SendMoneyLinearDashboardViewModel::class.java)

    val contactPer = 1
    val cameraPer = 2
    private val vs: ViewStub by lazy {
        findViewById(R.id.vsRecentBeneficiaries)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewStub()
        setObservers()
        setupRecycleView()
        setData()
    }

    private fun initViewStub() {
        vs.layoutResource = R.layout.layout_recent_beneficiaries_recylcerview
        vs.visibility = View.VISIBLE
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private fun setupRecycleView() {
        getBinding().recyclerOptions.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large),
                1,
                true
            )
        )
        viewModel.dashboardAdapter.onItemClickListener = itemClickListener
        viewModel.recentsAdapter.allowFullItemClickListener = true
        viewModel.recentsAdapter.setItemListener(itemClickListener)
    }

    private fun setData() {
        viewModel.dashboardAdapter.setData(viewModel.geSendMoneyOptions())
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Beneficiary) {
                when (data.beneficiaryType) {
                    SendMoneyBeneficiaryType.YAP2YAP.type -> startY2YTransfer(data, false, pos)
                    else -> startMoneyTransfer(data, pos)
                }
            } else if (data is SendMoneyLinearOptions) {
                viewModel.clickEvent.setValue(data.type.ordinal)
            }
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            SendMoneyCategoryType.SendMoneyToYAPContacts.ordinal -> {
                checkPermission(contactPer)
            }
            SendMoneyCategoryType.SendMoneyToLocalBank.ordinal -> {
                startSendMoneyFlow(SendMoneyTransferType.LOCAL.name)
            }
            SendMoneyCategoryType.SendMoneyToInternational.ordinal -> {
                startSendMoneyFlow(SendMoneyTransferType.INTERNATIONAL.name)
            }
            SendMoneyCategoryType.SendMoneyToHomeCountry.ordinal -> {
                launchActivityForResult<SMHomeCountryActivity> { _, _ ->
                    if (!viewModel.dashboardAdapter.datas
                            .isNullOrEmpty() && SessionManager.homeCountry2Digit != "AE"
                    ) {
                        // TODO--we will update it only when we will get true (if country changed successfully)
                        viewModel.dashboardAdapter.datas
                            .find { date -> date.name == getString(Strings.screen_send_money_home_label) }
                            ?.let { option ->
                                val index = viewModel.dashboardAdapter.datas.indexOf(option)
                                option.image = CurrencyUtils.getFlagDrawable(
                                    context,
                                    SessionManager.homeCountry2Digit
                                )
                                viewModel.dashboardAdapter.update(index)
                            }
                    }
                }

            }
            SendMoneyCategoryType.SendMoneyQRCode.ordinal -> {
                checkPermission(cameraPer)
            }
            R.id.tvrecentTransfer, R.id.hiderecentext -> {
                viewModel.state.isRecentsVisible.set(getBinding().hiderecentext.visibility == View.VISIBLE)
                vs.visibility =
                    if (getBinding().hiderecentext.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
    }

    private fun checkPermission(type: Int) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                if (type == cameraPer) {
                    startQrFragment()
                } else
                    openY2YScreen()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                if (grantedPermission.contains(Manifest.permission.CAMERA))
                    startQrFragment()
                else
                    openY2YScreen()
            }

            override fun onPermissionDenied() {
                if (type == cameraPer) {
                    showToast(
                        Translator.getString(
                            context,
                            Strings.screen_send_money_display_permission_text
                        )
                    )
                } else
                    openY2YScreen()
            }

            override fun onPermissionDeniedBySystem() {
                if (type == cameraPer) {
                    showToast(
                        Translator.getString(
                            context,
                            Strings.screen_send_money_display_permission_text
                        )
                    )
                } else
                    openY2YScreen()
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_NOTIFY_BENEFICIARY_LIST, RequestCodes.REQUEST_Y2Y_TRANSFER, RequestCodes.REQUEST_TRANSFER_MONEY -> {
                    if (data?.getBooleanExtra(Constants.MONEY_TRANSFERED, false) == true) {
                        finish()
                    }
                }
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
            R.id.ivRightIcon -> {
                launchActivity<SMBeneficiaryParentActivity>(
                    type = FeatureSet.SEND_MONEY,
                    requestCode = RequestCodes.REQUEST_TRANSFER_MONEY
                ) {
                    putExtra(
                        ExtraKeys.SEND_MONEY_TYPE.name,
                        SendMoneyTransferType.ALL_Y2Y_SM.name
                    )
                }
            }
        }
    }

    /**
     * Open New Screens Start
     */
    private fun startQrFragment() {
        trackEventWithScreenName(FirebaseEvent.SEND_QR_CODE)
        startFragmentForResult<ScanQRCodeFragment>(ScanQRCodeFragment::class.java.name) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val beneficiary =
                    data?.getParcelableExtra<Beneficiary>(Beneficiary::class.java.name)
                startY2YTransfer(beneficiary, true)
            }
        }
    }

    private fun startMoneyTransfer(beneficiary: Beneficiary?, position: Int) {
        launchActivity<BeneficiaryFundTransferActivity>(
            requestCode = RequestCodes.REQUEST_TRANSFER_MONEY,
            type = beneficiary.getBeneficiaryTransferType()
        ) {
            putExtra(Constants.BENEFICIARY, beneficiary)
            putExtra(Constants.POSITION, position)
            putExtra(Constants.IS_NEW_BENEFICIARY, false)
        }
    }

    private fun startY2YTransfer(
        beneficiary: Beneficiary?,
        fromQR: Boolean = false,
        position: Int = 0
    ) {
        launchActivity<YapToYapDashboardActivity>(
            requestCode = RequestCodes.REQUEST_Y2Y_TRANSFER,
            type = FeatureSet.Y2Y_TRANSFER
        ) {
            putExtra(Beneficiary::class.java.name, beneficiary)
            putExtra(ExtraKeys.IS_FROM_QR_CONTACT.name, fromQR)
            putExtra(ExtraKeys.Y2Y_BENEFICIARY_POSITION.name, position)
        }
    }

    private fun startSendMoneyFlow(SendMoneyCategoryType: String) {
        launchActivity<SMBeneficiaryParentActivity>(requestCode = RequestCodes.REQUEST_NOTIFY_BENEFICIARY_LIST) {
            putExtra(
                ExtraKeys.SEND_MONEY_TYPE.name,
                SendMoneyCategoryType
            )
        }
    }

    private fun openY2YScreen() {
        launchActivity<YapToYapDashboardActivity>(
            requestCode = RequestCodes.REQUEST_Y2Y_TRANSFER,
            type = FeatureSet.YAP_TO_YAP
        ) {
            putExtra(ExtraKeys.IS_Y2Y_SEARCHING.name, false)
        }
    }

    /**
     * Open New Screens End
     */

    override fun getBinding() = getDataBindingView<ActivitySendMoneyLinearDashboardBinding>()

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}