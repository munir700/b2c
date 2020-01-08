package co.yap.household.onboarding.dashboard.home.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.household.onboarding.dashboard.home.interfaces.IHouseholdHome
import co.yap.household.onboarding.dashboard.home.viewmodels.HouseholdHomeViewModel
import co.yap.household.onboarding.dashboard.main.fragments.HouseholdDashboardBaseFragment
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants

class HouseholdHomeFragment : HouseholdDashboardBaseFragment<IHouseholdHome.ViewModel>(),
    IHouseholdHome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_household_home

    override val viewModel: IHouseholdHome.ViewModel
        get() = ViewModelProviders.of(this).get(HouseholdHomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.ivMenu -> {
                parentView?.toggleDrawer()
            }
            R.id.ivSearch -> {
            }
        }
    }

    private val viewStateObserver = Observer<Int> {
        when (it) {
            Constants.EVENT_LOADING -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.LOADING
            }
            Constants.EVENT_EMPTY -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
            Constants.EVENT_CONTENT -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewState.removeObserver(viewStateObserver)
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private fun getViewBinding(): FragmentHouseholdHomeBinding {
        return (viewDataBinding as FragmentHouseholdHomeBinding)
    }
}