package co.yap.modules.dashboard.cards.reordercard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.reordercard.interfaces.IRenewCard
import co.yap.modules.dashboard.cards.reordercard.viewmodels.RenewCardViewModel
import co.yap.yapcore.BR

class ReorderCardFragment : ReorderCardBaseFragment<IRenewCard.ViewModel>(), IRenewCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_reorder_card

    override val viewModel: IRenewCard.ViewModel
        get() = ViewModelProviders.of(this).get(RenewCardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyArguments()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.reorderCardSuccess.observe(this, Observer {
            if (it) {
                findNavController().navigate(R.id.action_reorderCardFragment_to_reorderCardSuccessFragment)
            }
        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                viewModel.state.isAddressConfirm.set(true)
                viewModel.address.address1 = viewModel.state.cardAddressSubTitle.get()
                viewModel.address.address2 = viewModel.state.cardAddressTitle.get()
            }
            R.id.btnConfirmPurchase -> {
                viewModel.requestReorderCard()
            }
            R.id.tvChangeLocation -> {
                findNavController().navigate(R.id.action_reorderCardFragment_to_addressSelectionFragment)
            }
        }
    }

    private fun getMyArguments() {
        viewModel.state.cardType.set(arguments?.let {
            ReorderCardFragmentArgs.fromBundle(it).cardType
        } as String)

        // address arguments
        val physicalCardAddressTitle = arguments?.let {
            ReorderCardFragmentArgs.fromBundle(it).newDeliveryAddressTitle
        } as String

        if (physicalCardAddressTitle != " ") {
            viewModel.state.cardAddressTitle.set(physicalCardAddressTitle)
            viewModel.state.isAddressConfirm.set(true)
        }

        val physicalCardAddressSubTitle = arguments?.let {
            ReorderCardFragmentArgs.fromBundle(it).newDeliveryAddressSubTitle
        } as String

        if (physicalCardAddressSubTitle != " ") {
            viewModel.state.cardAddressSubTitle.set(physicalCardAddressTitle)
        }

        // location lat lon arguments
        val longitude = arguments?.let {
            ReorderCardFragmentArgs.fromBundle(it).longitude
        } as String
        if (longitude.isNotEmpty()) {
            viewModel.address.longitude = longitude.toDoubleOrNull()
        }

        val latitude = arguments?.let {
            ReorderCardFragmentArgs.fromBundle(it).latitude
        } as String

        if (latitude.isNotEmpty()) {
            viewModel.address.latitude = latitude.toDoubleOrNull()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
        viewModel.reorderCardSuccess.removeObservers(this)
    }
}