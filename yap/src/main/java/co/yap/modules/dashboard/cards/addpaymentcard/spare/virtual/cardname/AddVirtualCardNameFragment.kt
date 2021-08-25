package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    }
    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.cardName.addOnPropertyChangedCallback(stateObserver)
    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (viewModel.observeCardNameLength(viewModel.state.cardName.get() ?: "")) {
                viewModel.clickEvent.call()
            }
        }
    }

    private val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnNext -> {
                val action =
                    AddVirtualCardNameFragmentDirections.actionAddVirtualCardNameFragmentToAddSpareCardFragment(
                        getString(R.string.screen_spare_card_landing_display_text_virtual_card),
                        "",
                        "",
                        "",
                        "",
                        false,
                        viewModel.state.cardName.get() ?: ""
                    )

                navigate(action)
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.state.cardName.removeOnPropertyChangedCallback(stateObserver)
    }



    override fun getBindings(): FragmentAddVirtualCardNameBinding {
        return viewDataBinding as FragmentAddVirtualCardNameBinding
    }
}


