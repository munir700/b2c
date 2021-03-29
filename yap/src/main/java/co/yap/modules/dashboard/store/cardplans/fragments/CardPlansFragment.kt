package co.yap.modules.dashboard.store.cardplans.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansViewModel
import co.yap.repositories.InviteFriendRepository
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils.getBody
import co.yap.yapcore.interfaces.OnItemClickListener

class CardPlansFragment : CardPlansBaseFragment<ICardPlans.ViewModel>(), ICardPlans.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_plans

    override val viewModel: CardPlansViewModel
        get() = ViewModelProviders.of(this).get(CardPlansViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.iniVideoView(getBindings().cardAnimation)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
        viewModel.cardAdapter.onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                clickOnCardPlan(data, pos)
            }
        }
        viewModel.parentViewModel?.setViewDimensions(50,getBindings().rvCardplans)

    }

    private val onClickObserver = Observer<Int> { id ->
        when (id) {
            R.id.ivShare -> shareInfo()
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

    private fun shareInfo() {
        InviteFriendRepository().inviteAFriend()
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody(requireContext()))
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    override fun navigateToFragment(data: String) {
        navigate(destinationId = R.id.action_cardPlansFragment_to_cardPlanViewerFragment,
            args = bundleOf((viewModel.parentViewModel?.cardTag ?: "CARD-TAG") to data))
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(onClickObserver)
    }

    override fun getBindings(): FragmentCardPlansBinding =
        viewDataBinding as FragmentCardPlansBinding

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }
    override fun onResume() {
        super.onResume()
        getBindings().cardAnimation.start()
    }
}