package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhProfileBinding
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileFragmentDirections
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toast
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option
import kotlinx.android.synthetic.main.fragment_hh_profile.*

class HHProfileFragment :
    BaseNavViewModelFragment<FragmentHhProfileBinding, IHHProfile.State, HHProfileVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_hh_profile
    override fun getToolBarTitle() = "Card holder's profile"
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setHasOptionsMenu(true)
        viewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_options, menu)
    }

    fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> multiStateView?.viewState = MultiStateView.ViewState.LOADING
            Status.ERROR -> multiStateView?.viewState = MultiStateView.ViewState.ERROR
            Status.SUCCESS -> multiStateView?.viewState =
                MultiStateView.ViewState.CONTENT
            else -> multiStateView?.viewState = MultiStateView.ViewState.EMPTY

        }

    }
}