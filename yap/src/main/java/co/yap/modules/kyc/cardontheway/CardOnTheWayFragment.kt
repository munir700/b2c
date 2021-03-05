package co.yap.modules.kyc.cardontheway

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.kyc.fragments.KYCChildFragment

class CardOnTheWayFragment : KYCChildFragment<ICardOnTheWay.ViewModel>(), ICardOnTheWay.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_on_the_way

    override val viewModel: CardOnTheWayViewModel
        get() = ViewModelProviders.of(this).get(CardOnTheWayViewModel::class.java)
}