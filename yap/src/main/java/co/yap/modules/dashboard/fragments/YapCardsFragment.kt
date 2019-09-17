package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.interfaces.IYapCards
import co.yap.modules.dashboard.viewmodels.YapCardsViewModel
import co.yap.modules.onboarding.activities.CreatePasscodeActivity
import co.yap.modules.onboarding.constants.Constants
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })

        cardsTitle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_yapCards_to_addPaymentCardActivity)
            }
        })
    }
}