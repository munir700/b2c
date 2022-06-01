package co.yap.modules.frame

import android.app.Application
import co.yap.yapcore.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class FrameActivityViewModel @Inject constructor(application: Application):
    BaseViewModel<IFrameActivity.State>(application),
    IFrameActivity.ViewModel {
    override val state: FrameActivityState = FrameActivityState()
}