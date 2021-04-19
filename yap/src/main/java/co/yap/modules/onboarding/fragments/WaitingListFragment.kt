package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IWaitingList
import co.yap.modules.onboarding.viewmodels.WaitingListViewModel
import co.yap.translation.Strings
import co.yap.widgets.video.ExoPlayerCallBack
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.inviteFriendIntent
import co.yap.yapcore.helpers.showSnackBar
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import kotlinx.android.synthetic.main.fragment_waiting_list.*

class WaitingListFragment : BaseBindingFragment<IWaitingList.ViewModel>(), IWaitingList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_waiting_list

    override val viewModel: IWaitingList.ViewModel
        get() = ViewModelProviders.of(this).get(WaitingListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.requestWaitingRanking {
            showGainPointsNotification()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCoinAnimation()
    }

    private fun setupCoinAnimation() {
        ImageBinding.loadGifImageView(ivYapAnimation, R.raw.wait_list_first, 1) {
            ImageBinding.loadGifImageView(imageView = ivYapAnimation,
                resource = R.raw.wait_list_second,
                loopCount = 10000,
                delayBetweenLoop = 0L) {
            }
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnShare -> {
                context?.inviteFriendIntent()
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
}
