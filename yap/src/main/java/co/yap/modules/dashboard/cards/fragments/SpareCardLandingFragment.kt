package co.yap.modules.dashboard.cards.fragments


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.interfaces.ISpareCards
import co.yap.modules.dashboard.cards.viewmodels.SpareCardLandingViewModel
import co.yap.modules.dashboard.fragments.YapDashboardChildFragment

class SpareCardLandingFragment : YapDashboardChildFragment<ISpareCards.ViewModel>(),
    ISpareCards.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_spare_card_landing

    override val viewModel: ISpareCards.ViewModel
        get() = ViewModelProviders.of(this).get(SpareCardLandingViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })
    }
}