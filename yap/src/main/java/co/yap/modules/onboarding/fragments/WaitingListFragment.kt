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
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.inviteFriendIntent
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.helpers.showSnackBar
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
        setupCoinAnimation()
    }

    private fun setupCoinAnimation() {
        ImageBinding.loadGifImageView(ivYapAnimation, R.raw.wait_list_first, 1) {
            ImageBinding.loadGifImageView(imageView = ivYapAnimation,
                resource = R.raw.wait_list_second,
                loopCount = 1,
                delayBetweenLoop = 0L, isLoop = true) {
            }
        }
    }

    private fun runAnimation(){
        CoroutineScope(Main).launch {
            getBinding().dtvRankOne.setValue(viewModel.state.rankList?.get(1)?.parseToInt()?:0)
            getBinding().dtvRankTwo.setValue(viewModel.state.rankList?.get(2)?.parseToInt()?:0).apply { delay(200) }
            getBinding().dtvRankThree.setValue(viewModel.state.rankList?.get(3)?.parseToInt()?:0).apply { delay(350) }
            getBinding().dtvRankFour.setValue(viewModel.state.rankList?.get(4)?.parseToInt()?:0).apply { delay(500) }
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

    fun getBinding(): FragmentWaitingListBinding = viewDataBinding as FragmentWaitingListBinding
}
