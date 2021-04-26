package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentWaitingListBinding
import co.yap.modules.onboarding.interfaces.IWaitingList
import co.yap.modules.onboarding.viewmodels.WaitingListViewModel
import co.yap.repositories.InviteFriendRepository
import co.yap.translation.Strings
import co.yap.widgets.video.ExoPlayerCallBack
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.share
import co.yap.yapcore.helpers.showSnackBar
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import kotlinx.android.synthetic.main.fragment_waiting_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitingListFragment : BaseBindingFragment<IWaitingList.ViewModel>(), IWaitingList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_waiting_list

    override val viewModel: IWaitingList.ViewModel
        get() = ViewModelProviders.of(this).get(WaitingListViewModel::class.java)

    var firstVideoPlayed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.requestWaitingRanking {
            if (it) showGainPointsNotification()
            runAnimation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setYapAnimation()
    }

    private fun setYapAnimation() {
        andExoPlayerView.setSource(co.yap.yapcore.R.raw.waiting_list_first_part)
        andExoPlayerView?.player?.repeatMode = Player.REPEAT_MODE_OFF
        andExoPlayerView.setExoPlayerCallBack(object : ExoPlayerCallBack {
            override fun onError() {
                andExoPlayerView.setSource(co.yap.yapcore.R.raw.card_on_its_way)
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {
            }

            override fun onPositionDiscontinuity(reason: Int) {}

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        andExoPlayerView.visibility = View.VISIBLE
                    }

                    Player.STATE_ENDED -> {
                        if (!firstVideoPlayed) {
                            andExoPlayerView.visibility = View.INVISIBLE
                            andExoPlayerView.setSource(co.yap.yapcore.R.raw.waiting_list_second_part)
                            andExoPlayerView?.player?.repeatMode = Player.REPEAT_MODE_ALL
                            firstVideoPlayed = true
                        }
                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }
        })
    }

    private fun runAnimation() {
        CoroutineScope(Main).launch {
            getBinding().dtvRankOne.setValue(viewModel.state.rankList?.get(0) ?: 0)
            getBinding().dtvRankTwo.setValue(viewModel.state.rankList?.get(1) ?: 0)
                .apply { delay(100) }
            getBinding().dtvRankThree.setValue(viewModel.state.rankList?.get(2) ?: 0)
                .apply { delay(150) }
            getBinding().dtvRankFour.setValue(viewModel.state.rankList?.get(3) ?: 0)
                .apply { delay(200) }
            getBinding().dtvRankFive.setValue(viewModel.state.rankList?.get(4) ?: 0)
                .apply { delay(250) }
            getBinding().dtvRankSix.setValue(viewModel.state.rankList?.get(5) ?: 0)
                .apply { delay(300) }
            getBinding().dtvRankSeven.setValue(viewModel.state.rankList?.get(6) ?: 0)
                .apply { delay(350) }
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnShare -> {
                InviteFriendRepository().inviteAFriend()
                requireContext().share(
                    text = getString(
                        Strings.screen_invite_friend_display_text_share_url,
                        Utils.getAdjustURL()
                    )
                )
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun showGainPointsNotification() {
        showSnackBar(
            msg = getString(
                Strings.screen_waiting_list_display_text_notification_jump_rank,
                viewModel.state.gainPoints?.get() ?: "0"
            ),
            viewBgColor = R.color.colorLightGreen,
            colorOfMessage = R.color.colorDarkAqua,
            gravity = Gravity.TOP,
            duration = 10000,
            marginTop = 0
        )
    }

    fun getBinding(): FragmentWaitingListBinding = viewDataBinding as FragmentWaitingListBinding
}
