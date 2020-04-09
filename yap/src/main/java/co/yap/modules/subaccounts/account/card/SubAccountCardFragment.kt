package co.yap.modules.subaccounts.account.card

import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileFragment
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class SubAccountCardFragment :
    BaseRecyclerViewFragment<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountCardFragment.Adapter, SubAccount>(), OnItemDragDropListener {

    var dragAndDropManager: DragAndDropManager? = null
    override fun getBindingVariable() = BR.subAccountCardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_card
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setHasOptionsMenu(true)
        dragAndDropManager = DragAndDropManager(this)
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
            startFragment(HHSalaryProfileFragment::class.java.name)
//            navigateForwardWithAnimation(SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment())
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)


    }

    override fun onItemDrag(view: View, event: DragEvent, data: Any): Boolean? {
        return dragAndDropManager?.onItemDrag(view, event, data)
    }

    override fun onItemLongClick(view: View, pos: Int, id: Long, data: Any): Boolean? {
        if (pos == 0) {
            return dragAndDropManager?.onItemLongClick(view)
        }
        return true
    }

    class Adapter(mValue: MutableList<SubAccount>, navigation: NavController?) :
        BaseRVAdapter<SubAccount, SubAccountCardItemVM, Adapter.ViewHolder>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
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

        override fun getViewModel(viewType: Int) = SubAccountCardItemVM()
        override fun getVariableId() = BR.subAccountCardItemVm

        class ViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<SubAccount, SubAccountCardItemVM>(view, viewModel, mDataBinding)

    }

    override fun onItemDrop(data: Any) {
        val subAccount = data as SubAccount
        subAccount.accountType?.let {
            startFragment(HHSalaryProfileFragment::class.java.name)
        }
    }

}