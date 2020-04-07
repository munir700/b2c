package co.yap.modules.subaccounts.account.card

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragmentDirections
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class SubAccountCardFragment :
    BaseRecyclerViewFragment<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountCardFragment.Adapter, SubAccount>() {

    override fun getBindingVariable() = BR.subAccountCardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_card
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
//        Card is on the way (in purple text) and it will be non-clickable.
//        In case household user has declined the request of the employer. Declined by <first_name> will be displayed in red text.
//        then Card is active! Will be displayed in purple text
        val subAccount = data as SubAccount
        subAccount.accountType?.let {
            navigateForwardWithAnimation(SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment())
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)


    }

    class Adapter(mValue: MutableList<SubAccount>, navigation: NavController?) :
        BaseRVAdapter<SubAccount, SubAccountCardItemVM, Adapter.ViewHolder>(
            mValue,
            navigation
        ) {

        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = SubAccountCardItemVM()
        override fun getVariableId() = BR.subAccountCardItemVm

        class ViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<SubAccount, SubAccountCardItemVM>(view, viewModel, mDataBinding)
    }

}