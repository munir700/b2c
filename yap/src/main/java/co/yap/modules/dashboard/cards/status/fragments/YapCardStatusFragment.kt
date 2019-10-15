package co.yap.modules.dashboard.cards.status.fragments

import android.app.Activity
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
import co.yap.modules.dashboard.cards.status.interfaces.IYapCardStatus
import co.yap.modules.dashboard.cards.status.viewmodels.YapCardStatusViewModel
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.dashboard.store.fragments.YapStoreFragmentDirections
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.widget_step_indicator_layout.*


class YapCardStatusFragment : BaseBindingFragment<IYapCardStatus.ViewModel>(), IYapCardStatus.View {

    private val args: YapCardStatusFragmentArgs by navArgs()
    val EVENT_CREATE_CARD_PIN: Int get() = 13
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
        viewModel.state.title.set(if (card.cardType == "DEBIT") "Primary card" else "Spare physical card")
        viewModel.state.cardType.set(if (card.cardType == "DEBIT") "Primary card" else "Spare physical card")
        viewModel.state.message.set(if (card.cardType == "DEBIT") "Your Primary card is on its way" else "Your Spare physical card is on its way")

        when (card.deliveryStatus?.let { CardDeliveryStatus.valueOf(it) }) {
            CardDeliveryStatus.BOOKED -> {
                tbBtnOneOrdered.setImageResource(R.drawable.ic_tick)
                tvOrdered.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorPrimary
                    )
                )
            }
            CardDeliveryStatus.ORDERED -> {
                tbBtnOneOrdered.setImageResource(R.drawable.ic_tick)
                tvOrdered.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorPrimary
                    )
                )

            }
            CardDeliveryStatus.SHIPPING -> {

                tbBtnOneOrdered.setImageResource(R.drawable.ic_tick)
                tvOrdered.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorPrimary
                    )
                )

                tbProgressBarBuilding.progress = 100
                tbBtnBuilding.setImageResource(R.drawable.ic_tick)
                tvBuilding.text = "Built"
                tvBuilding.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
            }
            CardDeliveryStatus.SHIPPED -> {
                viewModel.state.message.set(if (card.cardType == "DEBIT") "Your Primary card is shipped" else "Your Spare physical card is shipped")
                tbBtnOneOrdered.setImageResource(R.drawable.ic_tick)
                tvOrdered.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorPrimary
                    )
                )

                tbProgressBarBuilding.progress = 100
                tbBtnBuilding.setImageResource(R.drawable.ic_tick)
                tvBuilding.text = "Built"
                tvBuilding.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )


                viewModel.state.shippingProgress = 100
                tbBtnShipping.setImageResource(R.drawable.ic_tick)
                tvShipping.text = "Shipped"
                tvShipping.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
                viewModel.state.valid = true
            }
            CardDeliveryStatus.FAILED -> {

            }
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val action =
                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
            view.findNavController().navigate(action)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.btnActivateCard -> {
                startActivityForResult(
                    SetCardPinWelcomeActivity.newIntent(
                        requireContext(),
                        card.cardSerialNumber
                    ), EVENT_CREATE_CARD_PIN
                )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EVENT_CREATE_CARD_PIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val isPinCreated: Boolean? =
                        data?.getBooleanExtra(Constants.isPinCreated, false)
                    if (isPinCreated!!) {
                        co.yap.modules.others.constants.Constants.isPinCreated = true
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}