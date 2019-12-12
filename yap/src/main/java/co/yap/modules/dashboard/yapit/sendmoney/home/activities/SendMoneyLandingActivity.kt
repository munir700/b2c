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
import co.yap.yapcore.helpers.Utils
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
        viewModel.isSearching.value = intent.getBooleanExtra(searching, false)
        viewModel.isSearching.value?.let {
            viewModel.state.isSearching.set(it)
            setSearchView(it)
        }
        setObservers()
    }

    private fun initComponents() {
        getBinding().layoutBeneficiaries.rvAllBeneficiaries.adapter =
            AllBeneficiriesAdapter(mutableListOf())
        initSwipeListener()
    }

    private fun setObservers() {
        //Beneficiaries list observer
        viewModel.allBeneficiariesLiveData.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                // show and hide views for no beneficiary
                viewModel.state.isNoBeneficiary.set(true)
                viewModel.state.hasBeneficiary.set(false)
            } else {
                viewModel.state.isNoBeneficiary.set(false)
                viewModel.state.hasBeneficiary.set(true)
                getAdaptor().setList(it)
                if (viewModel.state.isSearching.get()!!)
                    Utils.requestKeyboard(getSearchView(), request = false, forced = true)
            }
        })
        //Beneficiaries list Search Query observer
        viewModel.searchQuery.observe(this, Observer {
            getAdaptor().filter.filter(it)
        })

        //Recent Beneficiaries list observer
        viewModel.recentTransferData.observe(this, Observer {
            if (it.isNullOrEmpty()) return@Observer
            val adapter = RecentTransferAdaptor(
                it.toMutableList(),
                    null
                )
            adapter.onItemClickListener = recentItemClickListener
            viewModel.adapter.set(adapter)

        })
        //Searching Beneficiaries list Results Count observer
        viewModel.isSearching.value?.let { isSearching ->
            if (isSearching) {
                getAdaptor().filterCount.observe(this, Observer {
                    getBinding().layoutBeneficiaries.txtError.visibility =
                        if (it == 0) View.VISIBLE else View.GONE
                })
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
        if (viewModel.adapter.get() == null && !viewModel.state.isSearching.get()!!) // use `!!` because its default value is set it can never be null
            viewModel.requestRecentBeneficiaries()
    }

    private val recentItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Beneficiary)
                startMoneyTransfer(data)
        }
    }

    private fun initSwipeListener() {
        onTouchListener = RecyclerTouchListener(this, rvAllBeneficiaries)
            .setClickable(
                object : RecyclerTouchListener.OnRowClickListener {
                    override fun onRowClicked(position: Int) {
                        val beneficiary = viewModel.allBeneficiariesLiveData.value?.get(position)
                        startMoneyTransfer(beneficiary)
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
                        val beneficiary = viewModel.allBeneficiariesLiveData.value?.get(position)
                        beneficiary?.let { confirmDeleteBeneficiary(it) }
                    }
                    R.id.btnEdit -> {
                        val beneficiary = viewModel.allBeneficiariesLiveData.value?.get(position)
                        openEditBeneficiary(beneficiary)
                    }
                }
            }
    }

    private fun startMoneyTransfer(beneficiary: Beneficiary?) {
        showToast("data ${beneficiary?.title}")
    }

    private fun openEditBeneficiary(beneficiary: Beneficiary?) {
        Utils.hideKeyboard(getSearchView())
        beneficiary?.let {
            val intent = EditBeneficiaryActivity.newIntent(context = this)
            val bundle = Bundle()
            bundle.putParcelable(Beneficiary::class.java.name, beneficiary)
            intent.putExtra(Bundle_EXTRA, bundle)
            startActivityForResult(intent, REQUEST_CODE)
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
        // calling this function on resume because whenever user go for search and back to home it will set the searchView according to its state
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