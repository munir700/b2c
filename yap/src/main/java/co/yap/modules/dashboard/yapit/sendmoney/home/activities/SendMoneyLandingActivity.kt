package co.yap.modules.dashboard.yapit.sendmoney.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivitySendMoneyLandingBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.SendMoneyHomeActivity
import co.yap.modules.dashboard.yapit.sendmoney.home.adapters.AllBeneficiriesAdapter
import co.yap.modules.dashboard.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.yapit.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.widgets.swipe_lib.SwipeCallBack
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.hideKeyboard
import co.yap.yapcore.helpers.toast
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.layout_beneficiaries.*

class SendMoneyLandingActivity : BaseBindingActivity<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View,
    SwipeCallBack {
    var positionToDelete = 0
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_send_money_landing

    override val viewModel: ISendMoneyHome.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyHomeScreenViewModel::class.java)

    companion object {
        private const val searching = "searching"
        const val data = "payLoad"
        fun newIntent(context: Context): Intent {
            return Intent(context, SendMoneyLandingActivity::class.java)
        }

        fun getIntent(context: Context, isSearching: Boolean, payLoad: Parcelable?): Intent {
            val intent = Intent(context, SendMoneyLandingActivity::class.java)
            if (payLoad != null)
                intent.putExtra(data, payLoad)

            intent.putExtra(searching, isSearching)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setObservers()
        viewModel.isSearching.value?.let { setSearchView(it) }
    }

    private fun initComponents() {
        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter =
            AllBeneficiriesAdapter(mutableListOf(), this)
        getAdaptor().allowFullItemClickListener = true
        getAdaptor().setItemListener(listener)
    }

    private fun setObservers() {
        viewModel.allBeneficiariesLiveData.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                viewModel.state.isNoBeneficiary.set(true)
            } else {
                viewModel.state.isNoBeneficiary.set(false)
                getAdaptor().setList(it)
            }
        })
    }

    private fun setSearchView(show: Boolean) {
        getBinding().layoutBeneficiaries.layoutSearchView.ivSearch.visibility =
            if (!show) View.VISIBLE else View.GONE
        getBinding().layoutBeneficiaries.layoutSearchView.tvSearch.visibility =
            if (!show) View.VISIBLE else View.GONE
        getSearchView().visibility =
            if (!show) View.GONE else View.VISIBLE

        if (!show) {
            getSearchView().isIconified = true
            getBinding().run {
                getSearchView().setIconifiedByDefault(false)
            }
            getBinding().layoutBeneficiaries.tvCancel.visibility = View.GONE
        } else {
            getBinding().layoutBeneficiaries.tvCancel.visibility = View.VISIBLE
            getSearchView().isIconified = false
            getBinding().run { getSearchView().setIconifiedByDefault(false) }
            getSearchView().setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchQuery.value = query
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchQuery.value = newText
                    return true
                }
            })
            getSearchView().onFocusChangeListener =
                View.OnFocusChangeListener { view, hasFoucs -> if (!hasFoucs) view.hideKeyboard() }
        }
    }

    private fun setupRecent() {
//        if (viewModel.adapter.get() == null) {
//            viewModel.requestRecentBeneficiaries()
//            viewModel.recentTransferData.observe(this, Observer {
//                if (it.isEmpty()) {
//                    layoutRecent?.visibility = View.GONE
//                } else {
////                    viewModel.adapter.set(
////                        RecentTransferAdaptor(
////                            it.toMutableList(),
////                            findNavController()
////                        )
////                    )
//                    layoutRecent?.visibility = View.VISIBLE
//                }
//            })
//        } else {
//            if (viewModel.recentTransferData.value != null && viewModel.recentTransferData.value!!.isNotEmpty()) {
////                viewModel.adapter.set(
////                    RecentTransferAdaptor(
////                        viewModel.recentTransferData.value?.toMutableList()!!,
////                        findNavController()
////                    )
////                )
//                layoutRecent?.visibility = View.VISIBLE
//            }
//        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            //TODO: Start Sufyan Money Transfer flow
            showToast("On Full item clicked")
        }
    }

    override fun onSwipeEdit(beneficiary: Beneficiary) {
        //TODO: Using StartActivityForResult Navigate to Edit Beneficiary Screen Used by Irfan
        toast(beneficiary.title + " onSwipeEdit clicked")
    }

    override fun onSwipeDelete(beneficiary: Beneficiary, position: Int) {
        positionToDelete = position
        confirmDeleteBeneficiary(beneficiary)
    }


    private fun confirmDeleteBeneficiary(beneficiary: Beneficiary) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(
                Translator.getString(
                    this,
                    R.string.screen_send_money_display_text_delete
                )
            )
            .setMessage(
                Translator.getString(
                    this,
                    R.string.screen_send_money_display_text_delete_message
                )
            )
            .setPositiveButton(
                Translator.getString(
                    this,
                    R.string.common_button_yes
                )
            ) { dialog, which ->
                viewModel.requestDeleteBeneficiary(beneficiary.id)
            }

            .setNegativeButton(
                Translator.getString(
                    this,
                    R.string.common_button_cancel
                ),
                null
            )
            .show()
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.onDeleteSuccess.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        setupRecent()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.addContactsButton -> startActivity(SendMoneyHomeActivity.newIntent(this@SendMoneyLandingActivity))
                R.id.tbBtnAddBeneficiary -> startActivity(SendMoneyHomeActivity.newIntent(this@SendMoneyLandingActivity))
                R.id.clSearchBeneficiary -> {
                    viewModel.isSearching.value?.let { isSearching ->
                        if (!isSearching)
                            startActivity(getIntent(this, true, null))
                    }
                }
            }
        })

        viewModel.onDeleteSuccess.observe(this, Observer {
            getAdaptor().removeItemAt(positionToDelete)
        })
    }

    private fun getAdaptor(): AllBeneficiriesAdapter {
        return getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter as AllBeneficiriesAdapter
    }

    private fun getSearchView(): SearchView {
        return getBinding().layoutBeneficiaries.layoutSearchView.svBeneficiary
    }

    private fun getBinding(): ActivitySendMoneyLandingBinding {
        return (viewDataBinding as ActivitySendMoneyLandingBinding)
    }
}