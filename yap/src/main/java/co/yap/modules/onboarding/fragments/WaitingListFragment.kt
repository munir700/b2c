package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IWaitingList
import co.yap.modules.onboarding.viewmodels.WaitingListViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.inviteFriendIntent
import kotlinx.android.synthetic.main.fragment_waiting_list.*

class WaitingListFragment : BaseBindingFragment<IWaitingList.ViewModel>(), IWaitingList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_waiting_list

    override val viewModel: IWaitingList.ViewModel
        get() = ViewModelProviders.of(this).get(WaitingListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImageBinding.loadGifImageView(ivYapAnimation, R.raw.gif_yap_waiting_animation,2,3000L)
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
}
