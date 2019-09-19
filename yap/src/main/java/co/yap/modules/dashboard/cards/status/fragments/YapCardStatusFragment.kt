package co.yap.modules.dashboard.cards.status.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.activities.YapDashboardActivity
import co.yap.modules.dashboard.cards.status.interfaces.IYapCardStatus
import co.yap.modules.dashboard.cards.status.viewmodels.YapCardStatusViewModel
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.store.fragments.YapStoreFragmentDirections
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.BasePagingBindingRecyclerAdapter
import kotlinx.android.synthetic.main.widget_step_indicator_layout.*


class YapCardStatusFragment : BaseBindingFragment<IYapCardStatus.ViewModel>(), IYapCardStatus.View {

    private val args: YapCardStatusFragmentArgs by navArgs()
    lateinit var card: Card
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_card_status

    override val viewModel: IYapCardStatus.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardStatusViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        updateBindings()
    }

    private fun updateBindings() {
        card = args.cardInfo
        viewModel.state.title.set(card.cardName)
        viewModel.state.cardType.set(if (card.cardType == "DEBIT") "Primary card" else "Spare card")
        viewModel.state.message.set(if (card.status == "ACTIVE") "Your Primary card is shipped" else "Your Spare card is shipped")
        viewModel.state.valid = card.status == "ACTIVE"
        viewModel.state.shippingProgress = if (card.delivered) 100 else 0
        tbBtnShipping.setImageResource(if (card.delivered) R.drawable.ic_tick else R.drawable.ic_tick_disabled)
        tvShipping.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (card.delivered) R.color.colorPrimary else R.color.light_grey
            )
        )
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    val listener = object : BasePagingBindingRecyclerAdapter.OnItemClickListener {
        override fun onItemClick(view: View, data: Any?, pos: Int) {
            val action =
                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
            view.findNavController().navigate(action)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.btnActivateCard -> {
                startActivity(Intent(requireContext(), SetCardPinWelcomeActivity::class.java))
            }
            R.id.tbBtnBack -> {
                findNavController().navigateUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(false)
    }

    override fun onPause() {
        super.onPause()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(true)
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}