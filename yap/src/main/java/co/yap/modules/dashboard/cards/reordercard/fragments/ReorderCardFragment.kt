package co.yap.modules.dashboard.cards.reordercard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments.AddSpareCardFragmentArgs
import co.yap.modules.dashboard.cards.reordercard.interfaces.IRenewCard
import co.yap.modules.dashboard.cards.reordercard.viewmodels.RenewCardViewModel
import co.yap.yapcore.BR

class ReorderCardFragment : ReorderCardBaseFragment<IRenewCard.ViewModel>(), IRenewCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_reorder_card

    override val viewModel: IRenewCard.ViewModel
        get() = ViewModelProviders.of(this).get(RenewCardViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyArguments()
    }

    private fun getMyArguments() {
        viewModel.state.cardType.set(arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).cardType
        } as String)
    }

}