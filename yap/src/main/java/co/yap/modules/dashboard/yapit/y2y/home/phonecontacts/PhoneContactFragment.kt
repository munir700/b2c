package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentYapContactsBinding
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.PagingState
import kotlinx.android.synthetic.main.fragment_yap_store.*

class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_contacts

    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initComponents()
        initState()
    }

    private fun initComponents() {
        getBinding().recycler.adapter = PhoneContactsAdaptor { viewModel.retry() }
        //(getBinding().recycler.adapter as YapStoreAdaptor).setItemListener(listener)
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                recycler_stores.visibility = View.GONE
                txt_error.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                progress_bar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
            } else {
                txt_error.visibility = View.GONE
                progress_bar.visibility = View.GONE
                recycler_stores.visibility = View.VISIBLE
                (getBinding().recycler.adapter as PhoneContactsAdaptor)?.setState(state)
            }
        })
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    fun getBinding(): FragmentYapContactsBinding {
        return (viewDataBinding as FragmentYapContactsBinding)
    }
}