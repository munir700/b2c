package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapCards
import co.yap.modules.dashboard.viewmodels.YapCardsViewModel

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_spare_card_landing

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })
    }
}