package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddVirtualCardNameBinding
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment

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
        getBindings().etCardName.setText(viewModel.parentViewModel?.selectedCardName?.get())
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.cardName.observe(this, nameObserver)
    }

    private val nameObserver = Observer<String> {
        if (viewModel.observeCardNameLength(viewModel.state.cardName.value ?: ""))
            viewModel.clickEvent.call()
    }

    private val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnNext -> {
                viewModel.parentViewModel?.selectedCardName?.set(viewModel.state.cardName.value)
                viewModel.parentViewModel?.isFromBlockCard?.set(false)
                findNavController().navigate(R.id.action_addVirtualCardNameFragment_to_addSpareCardFragment)
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.state.cardName.removeObserver(nameObserver)
    }

    override fun getBindings(): FragmentAddVirtualCardNameBinding {
        return viewDataBinding as FragmentAddVirtualCardNameBinding
    }
}


