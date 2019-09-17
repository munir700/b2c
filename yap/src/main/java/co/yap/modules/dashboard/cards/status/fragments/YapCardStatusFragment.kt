package co.yap.modules.dashboard.cards.status.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.activities.YapDashboardActivity
import co.yap.modules.dashboard.cards.status.interfaces.IYapCardStatus
import co.yap.modules.dashboard.cards.status.viewmodels.YapCardStatusViewModel
import co.yap.modules.store.fragments.YapStoreFragmentDirections
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.BasePagingBindingRecyclerAdapter


class YapCardStatusFragment : BaseBindingFragment<IYapCardStatus.ViewModel>(), IYapCardStatus.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_card_status

    override val viewModel: IYapCardStatus.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardStatusViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }


    val listener = object : BasePagingBindingRecyclerAdapter.OnItemClickListener {
        override fun onItemClick(view: View, data: Any?, pos: Int) {
            val action =
                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
            view.findNavController().navigate(action)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgCard -> {
             viewModel.state.ordered
            }
            R.id.tvCardTitle -> {
                // stepIndicator.currentStep = stepIndicator.currentStep - 1
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(false)
    }

    override fun onPause() {
        super.onPause()
        if (activity is YapDashboardActivity)
            (activity as YapDashboardActivity).showHideBottomBar(true)
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}