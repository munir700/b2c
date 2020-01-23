package co.yap.modules.frame

import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R


class FrameActivity : BaseBindingActivity<IFrameActivity.ViewModel>(),
    IFrameActivity.View {
    companion object {
        val FRAGMENT_CLASS = "fragment_class"
        val SHOW_TOOLBAR = "_show_toolbar"
        val EXTRA = "_bundle_extras"
    }

    override fun getBindingVariable() = BR.frameActivityViewModel
    override fun getLayoutId() = R.layout.activity_frame
    override val viewModel: IFrameActivity.ViewModel
        get() = ViewModelProviders.of(this).get(FrameActivityViewModel::class.java)
}
