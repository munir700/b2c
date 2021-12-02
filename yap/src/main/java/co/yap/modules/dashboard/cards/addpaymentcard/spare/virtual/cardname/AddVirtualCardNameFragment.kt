package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddVirtualCardNameBinding
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment
import co.yap.yapcore.helpers.extentions.afterTextChanged

class AddVirtualCardNameFragment : AddPaymentChildFragment<IAddVirtualCardName.ViewModel>(),
    IAddVirtualCardName.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_virtual_card_name
    override val viewModel: AddVirtualCardNameViewModel
        get() = ViewModelProviders.of(this).get(AddVirtualCardNameViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCardImage(getBindings().imgCard)
        setTextWatcher()
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnNext -> {
                viewModel.parentViewModel?.selectedCardName?.set(viewModel.state.cardName.get())
                viewModel.parentViewModel?.isFromBlockCard?.set(false)
                navigateToNext(AddVirtualCardNameFragmentDirections.actionAddVirtualCardNameFragmentToAddSpareCardFragment())
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun getBindings(): FragmentAddVirtualCardNameBinding {
        return viewDataBinding as FragmentAddVirtualCardNameBinding
    }

    fun setTextWatcher() {
        getBindings().etCardName.afterTextChanged {
            if (viewModel.observeCardNameLength(it))
                viewModel.clickEvent.call()
        }
    }
}


