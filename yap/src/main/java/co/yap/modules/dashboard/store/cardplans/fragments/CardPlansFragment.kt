package co.yap.modules.dashboard.store.cardplans.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansViewModel
import co.yap.repositories.InviteFriendRepository
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.Utils.getBody
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardPlansFragment : CardPlansBaseFragment<ICardPlans.ViewModel>(), ICardPlans.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_plans

    override val viewModel: CardPlansViewModel
        get() = ViewModelProviders.of(this).get(CardPlansViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Default).launch {
            iniVideoView(getBindings().cardAnimation)
        }
        setObservers()
    }


    override fun setObservers() {
        viewModel.parentViewModel?.setViewDimensions(40, getBindings().cardAnimation)
        viewModel.clickEvent.observe(this, onClickObserver)
        viewModel.cardAdapter.onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                clickOnCardPlan(data, pos)
            }
        }
        setCardPlansDimensions()
    }

    private fun setCardPlansDimensions() {
        val dimensions: Int = Utils.getDimensionInPercent(requireContext(), false, 50)
        val params =
            getBindings().description.rvCardplans.layoutParams as LinearLayoutCompat.LayoutParams
        params.height = dimensions
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
            args = bundleOf((viewModel.parentViewModel?.cardTag ?: "CARD-TAG") to data),
            navOptions = navOptions {
                anim {
                    enter = co.yap.yapcore.R.anim.slide_up_from_bottom
                    popExit = R.anim.slide_out_to_bottom
                }
            })
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

    private suspend fun iniVideoView(video: VideoView) {
        CoroutineScope(Dispatchers.Default).launch {
            val uri =
                Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.video_all_card_plans)
            video.layoutParams =
                viewModel.parentViewModel?.setViewDimensions(32, video)
            video.setVideoURI(uri)
            video.start()
            launch {
                video.setOnCompletionListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    video.start()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getBindings().cardAnimation.start()
    }
}