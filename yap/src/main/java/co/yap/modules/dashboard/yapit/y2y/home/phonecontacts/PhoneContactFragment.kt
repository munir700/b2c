package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentPhoneContactsBinding
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.interfaces.OnItemClickListener

class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_phone_contacts

    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initState()
        initComponents()
    }

    private fun initComponents() {
        //getBinding().recycler.layoutManager = LinearLayoutManager(context!!)
        getBinding().recycler.adapter = PhoneContactsAdaptor { viewModel.retry() }
        (getBinding().recycler.adapter as PhoneContactsAdaptor).setItemListener(listener)
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                getBinding().recycler.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
            } else {
                getBinding().txtError.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
                (getBinding().recycler.adapter as PhoneContactsAdaptor)?.setState(state)
            }
        })
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.phoneContactLiveData.observe(this, Observer {
            (getBinding().recycler.adapter as PhoneContactsAdaptor).submitList(it)
            (getBinding().recycler.adapter as PhoneContactsAdaptor).setState(PagingState.DONE)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    private fun getBinding(): FragmentPhoneContactsBinding {
        return (viewDataBinding as FragmentPhoneContactsBinding)
    }
}