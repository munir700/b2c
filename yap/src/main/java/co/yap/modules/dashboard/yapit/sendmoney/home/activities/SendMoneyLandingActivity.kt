package co.yap.modules.dashboard.yapit.sendmoney.home.activities

import android.app.Activity
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
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity.EditBeneficiaryActivity
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity.EditBeneficiaryActivity.Companion.Bundle_EXTRA
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity.EditBeneficiaryActivity.Companion.REQUEST_CODE
import co.yap.modules.dashboard.yapit.sendmoney.home.adapters.AllBeneficiriesAdapter
import co.yap.modules.dashboard.yapit.sendmoney.home.adapters.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.yapit.sendmoney.home.viewmodels.SendMoneyHomeScreenViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.interfaces.OnItemClickListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import kotlinx.android.synthetic.main.layout_beneficiaries.*

class SendMoneyLandingActivity : BaseBindingActivity<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View {
    var positionToDelete = 0
    private lateinit var onTouchListener: RecyclerTouchListener
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
        viewModel.isSearching.value = intent.getBooleanExtra(searching, false)
        viewModel.isSearching.value?.let {
            viewModel.state.isSearching.set(it)
            setSearchView(it)
        }
    }

    private fun initComponents() {
        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter =
            AllBeneficiriesAdapter(mutableListOf())
        initSwipeListener()
    }

    private fun initSwipeListener() {
        onTouchListener = RecyclerTouchListener(this, rvAllBeneficiaries)
            .setClickable(
                object : RecyclerTouchListener.OnRowClickListener {
                    override fun onRowClicked(position: Int) {
                        //TODO: Start Sufyan Money Transfer flow
                        showToast("On Full item clicked")
                    }

                    override fun onIndependentViewClicked(
                        independentViewID: Int,
                        position: Int
                    ) {
                    }
                }).setSwipeOptionViews(R.id.btnEdit, R.id.btnDelete)
            .setSwipeable(
                R.id.foregroundContainer, R.id.swipe
            )
            { viewID, position ->
                when (viewID) {
                    R.id.btnDelete -> {
                        positionToDelete = position
                        val beneficiary =
                            viewModel.allBeneficiariesLiveData.value?.get(position)
                        beneficiary?.let { confirmDeleteBeneficiary(it) }
                    }
                    R.id.btnEdit -> {
                        //TODO: Using StartActivityForResult Navigate to Edit Beneficiary Screen Used by Irfan
                        val beneficiary =
                            viewModel.allBeneficiariesLiveData.value?.get(position)
                        beneficiary?.let {
                            val intent = EditBeneficiaryActivity.newIntent(context = this)
                            val bundle = Bundle()
                            bundle.putParcelable(Beneficiary::class.java.name, beneficiary)
                            intent.putExtra(Bundle_EXTRA, bundle)
                            startActivityForResult(intent, REQUEST_CODE)
                        }
                    }
                }
            }
    }

    private fun setObservers() {
        viewModel.allBeneficiariesLiveData.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                viewModel.state.isNoBeneficiary.set(true)
                viewModel.state.hasBeneficiary.set(false)
            } else {
                viewModel.state.isNoBeneficiary.set(false)
                viewModel.state.hasBeneficiary.set(true)
                getAdaptor().setList(it)
            }
        })
        viewModel.searchQuery.observe(this, Observer {
            getAdaptor().filter.filter(it)
        })
        viewModel.isSearching.value?.let { isSearching ->
            if (isSearching) {
                if (viewModel.getState().value != null && viewModel.getState().value != PagingState.LOADING) {
                    getAdaptor().filterCount.observe(this, Observer {
                        if (it == 0)
                            getBinding().layoutBeneficiaries.txtError.text =
                                if (viewModel.isSearching.value!!) "No result" else ""
                    })
                }
            }
        }
    }

    private fun setSearchView(show: Boolean) {
        if (!show) {
            getSearchView().isIconified = true
            getBinding().run {
                getSearchView().setIconifiedByDefault(false)
            }
        } else {
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
        }
    }

    private fun setupRecent() {
        if (viewModel.isSearching.value == true) {
            layoutRecent.visibility = View.GONE
        } else {
            if (viewModel.adapter.get() == null) {
                viewModel.requestRecentBeneficiaries()
                viewModel.recentTransferData.observe(this, Observer {
                    if (it.isNullOrEmpty()) {
                        layoutRecent?.visibility = View.GONE
                    } else {
                        val adapter = RecentTransferAdaptor(
                            it.toMutableList(),
                            null
                        )
                        adapter.onItemClickListener = recentItemClickListener
                        viewModel.adapter.set(adapter)

                        layoutRecent?.visibility = View.VISIBLE
                    }
                })
            } else {
                if (viewModel.recentTransferData.value != null && viewModel.recentTransferData.value!!.isNotEmpty()) {
//                viewModel.adapter.set(
//                    RecentTransferAdaptor(
//                        viewModel.recentTransferData.value?.toMutableList()!!,
//                        findNavController()
//                    )
//                )
                    layoutRecent?.visibility = View.VISIBLE
                }
            }
        }
    }


    private val recentItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Beneficiary) {
                showToast("data ${data.title}")
            }
        }
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
        rvAllBeneficiaries.removeOnItemTouchListener(onTouchListener)
        viewModel.clickEvent.removeObservers(this)
        viewModel.onDeleteSuccess.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        rvAllBeneficiaries.addOnItemTouchListener(onTouchListener)
        viewModel.state.isSearching.set(viewModel.isSearching.value!!)
        setSearchView(viewModel.isSearching.value!!)
        setupRecent()
        viewModel.clickEvent.observe(this, clickListener)

        viewModel.onDeleteSuccess.observe(this, Observer {
            getAdaptor().removeItemAt(positionToDelete)
        })
    }


    private val clickListener = Observer<Int> {
        when (it) {
            R.id.addContactsButton -> startActivity(SendMoneyHomeActivity.newIntent(this@SendMoneyLandingActivity)) //btn invoke add Beneficiary flow
            R.id.tbBtnAddBeneficiary -> startActivity(SendMoneyHomeActivity.newIntent(this@SendMoneyLandingActivity)) //toolbar invoke add Beneficiary flow
            R.id.tbBtnBack -> finish()
            R.id.layoutSearchView -> {
                viewModel.isSearching.value?.let { isSearching ->
                    if (!isSearching) {
                        startActivity(getIntent(this, true, null))
                    }
                }
            }
            R.id.tvCancel -> finish()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            when (requestCode) {
                REQUEST_CODE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        viewModel.requestAllBeneficiaries()
                    }
                }
            }
        }
    }
}