package co.yap.modules.dashboard.store.cardplans.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_card_plans.*

class CardPlansFragment : CardPlansBaseFragment<ICardPlans.ViewModel>(), ICardPlans.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_plans

    override val viewModel: CardPlansViewModel
        get() = ViewModelProviders.of(this).get(CardPlansViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniVideoView()
    }

    private fun iniVideoView() {
        getBindings().cardAnimation.layoutParams =
            viewModel.parentViewModel?.setViewDimensions(32, cardAnimation)
        getBindings().cardAnimation.setVideoURI(Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.video_all_card_plans))
        getBindings().cardAnimation.start()
        getBindings().cardAnimation.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            getBindings().cardAnimation.start()
        }
    }

    private fun eventListener() {
        viewModel.cardAdapter.onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                clickOnCardPlan(data, pos)
            }
        }
    }

    private fun clickOnCardPlan(data: Any, pos: Int) {
        if (data is CardPlans) {
            when (data.id) {
                Constants.PRIME_CARD_PLAN -> {
                    navigateToFragment(data.id)
                }
                Constants.METAL_CARD_PLAN -> {
                    navigateToFragment(data.id)
                }
            }
        }
    }

    override fun navigateToFragment(data: String) {
        navigate(destinationId = R.id.action_cardPlansFragment_to_cardPlanViewerFragment,
            args = bundleOf((viewModel.parentViewModel?.cardTag ?: "CARD-TAG") to data))
    }

    override fun getBindings(): FragmentCardPlansBinding =
        viewDataBinding as FragmentCardPlansBinding

}