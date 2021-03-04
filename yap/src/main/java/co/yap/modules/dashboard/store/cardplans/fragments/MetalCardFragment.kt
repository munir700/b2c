package co.yap.modules.dashboard.store.cardplans.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.interfaces.IPrimeMetalCard
import co.yap.modules.dashboard.store.cardplans.viewmodels.PrimeMetalCardViewModel

class MetalCardFragment : CardPlansBaseFragment<IPrimeMetalCard.ViewModel>(), IPrimeMetalCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_prime_metal_card

    override val viewModel: IPrimeMetalCard.ViewModel
        get() = ViewModelProviders.of(this).get(PrimeMetalCardViewModel::class.java)
}