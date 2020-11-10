package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.view.animation.Animation
import android.view.animation.AnimationUtils.makeInAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivitySendMoneyDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.homecountry.SMHomeCountryActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyDashboardViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyDashboard
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener


class SendMoneyDashboardActivity : BaseBindingActivity<ISendMoneyDashboard.ViewModel>(),
    ISendMoneyDashboard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_dashboard
    override val viewModel: SendMoneyDashboardViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyDashboardViewModel::class.java)

    lateinit var viewstub: ViewStub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewstub = findViewById<ViewStub>(R.id.vsRecentBeneficiaries)
        setObservers()
        setupRecycleView()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private fun setupRecycleView() {
        // val recycler = viewStub.findViewById<RecyclerView>(R.id.rvRecentBeneficiaries)
        //   getBinding().rvRecentBeneficiaries.adapter = viewModel.recentsAdapter
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
                // startFragment<ScanQRCodeFragment>(ScanQRCodeFragment::class.java.name)
            }
            R.id.showrecentext -> {
                handleViewStub(true)
            }
            R.id.hiderecentext -> {
                handleViewStub(false)
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

    fun handleViewStub(show: Boolean) {
        viewstub.layoutResource = R.layout.layout_recent_beneficiaries_recylcerview
        when (show) {
            true -> {
                viewstub.visibility = View.VISIBLE
                val animation: Animation = makeInAnimation(context, true)
                animation.setDuration(300)
                viewstub.startAnimation(animation)
                viewModel.state.labelTextVisibility = true
            }
            else -> {
                viewstub.visibility = View.GONE
                val animation: Animation = makeInAnimation(context, true)
                animation.setDuration(300)
                viewstub.startAnimation(animation)
                viewModel.state.labelTextVisibility = false
            }
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
