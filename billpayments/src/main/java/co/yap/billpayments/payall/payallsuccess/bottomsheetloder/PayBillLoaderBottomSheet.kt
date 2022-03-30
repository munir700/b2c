package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.billpayments.R
import co.yap.billpayments.databinding.BottomSheetPayAllBillsLoadingBinding
import co.yap.billpayments.utils.enums.LoaderStatus
import co.yap.widgets.video.ExoPlayerCallBack
import co.yap.yapcore.BR
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PayBillLoaderBottomSheet(val loadingState: MutableLiveData<LoaderStatus>) :
    BottomSheetDialogFragment(),
    IPayBillLoderBottomSheet.View {
    lateinit var viewDataBinding: BottomSheetPayAllBillsLoadingBinding
    override val viewModel: PayBillLoderBottomSheetViewModel by viewModels()
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    var firstVideoPlayed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_pay_all_bills_loading,
            container,
            false
        )
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setYapAnimation()
        loadingState.observe(this, Observer {
            when (it) {
                is LoaderStatus.LoadingState -> {
                    runAnimation()
                }
                is LoaderStatus.ErrorState -> {
                    dismissBottomSheetWithDelay()
                }
                is LoaderStatus.SuccessState -> {
                    viewModel.state.loadingPercentage.set(100)
                    viewModel.state.isResponceReceived.set(true)
                    dismissBottomSheetWithDelay()
                }
            }
        })
    }

    private fun dismissBottomSheetWithDelay() {

        Handler().postDelayed({ dismiss() }, 2000)

    }

    private fun initViews() {
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewModel.state.isResponceReceived.set(false)
        viewModel.state.loadingPercentage.set((5..12).random())
        viewDataBinding.executePendingBindings()
    }

    private fun getBinding() = viewDataBinding

    private fun setYapAnimation() {
        getBinding().andExoPlayerView.setSource(co.yap.yapcore.R.raw.waiting_list_first_part)
        getBinding().andExoPlayerView.player?.repeatMode = Player.REPEAT_MODE_OFF
        getBinding().andExoPlayerView.setExoPlayerCallBack(object : ExoPlayerCallBack {
            override fun onError() {
                if (firstVideoPlayed)
                    getBinding().andExoPlayerView.setSource(
                        co.yap.yapcore.R.raw.waiting_list_second_part
                    )
                else getBinding().andExoPlayerView.setSource(
                    co.yap.yapcore.R.raw.waiting_list_first_part
                )
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
                        getBinding().andExoPlayerView.visibility = View.VISIBLE
                    }
                    Player.STATE_ENDED -> {
                        if (!firstVideoPlayed) {
                            getBinding().andExoPlayerView.visibility = View.INVISIBLE
                            getBinding().andExoPlayerView.setSource(co.yap.yapcore.R.raw.waiting_list_second_part)
                            getBinding().andExoPlayerView.player?.repeatMode =
                                Player.REPEAT_MODE_ALL
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
        CoroutineScope(Dispatchers.Main).launch {
            delay(350)
        }
        updateProgressbar()
    }

    private fun updateProgressbar() {
        val timer = object : CountDownTimer(250000, 3) {
            override fun onTick(millisUntilFinished: Long) {
                if (viewModel.state.loadingPercentage.get() ?: 0 <= 87) {
                    viewModel.state.loadingPercentage.set(
                        viewModel.state.loadingPercentage.get()?.plus(12)
                    )
                }
            }

            override fun onFinish() {

            }
        }
        timer.start()
    }

    override fun showLoader(isVisible: Boolean) {

    }

    override fun showToast(msg: String) {}

    override fun showInternetSnack(isVisible: Boolean) {}

    override fun isPermissionGranted(permission: String) = false

    override fun requestPermissions() {}

    override fun getString(resourceKey: String) = ""

    override fun getScreenName() = ""

    override fun onNetworkStateChanged(isConnected: Boolean) {}
}
