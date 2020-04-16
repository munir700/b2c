package co.yap.modules.subaccounts.account.card


import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragmentDirections
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileFragment
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.onDrag
import co.yap.yapcore.helpers.extentions.startDrag
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.interfaces.OnItemDropListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.item_sub_account_card.*


class SubAccountCardFragment :
    BaseRecyclerViewFragment<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountCardFragment.Adapter, SubAccount>(), OnItemDropListener {

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
        val args = Bundle()
        args.putParcelable(SubAccount::class.simpleName, subAccount)
        subAccount.accountType?.let {
            when (it) {
                AccountType.B2C_ACCOUNT.name -> swipeViews(true)
                AccountType.B2C_HOUSEHOLD.name -> //showRequestDeclinedPopup(subAccount)
                    navigateForwardWithAnimation(
                        SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment(),
                        args
                    )
            }
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)
    }

    private fun swipeViews(swipe: Boolean) {
        if (swipe) {
            imgProfile.visibility = View.INVISIBLE
            layout_swipe_image.visibility = View.VISIBLE
            tv_drag_and_drop_label.visibility = View.VISIBLE
            animate(layout_swipe_image)
            animate(tv_drag_and_drop_label)
        } else {
            imgProfile.visibility = View.VISIBLE
            layout_swipe_image.visibility = View.INVISIBLE
            tv_drag_and_drop_label.visibility = View.INVISIBLE
        }
    }

    private fun animate(view: View) {
        YoYo.with(Techniques.SlideInDown)
            .duration(400)
            .repeat(0)
            .playOn(view)
    }

    override fun onItemDrag(view: View, pos: Int, event: DragEvent, data: Any): Boolean? {
        swipeViews(false)
        return onDrag(view, pos, event, data, this)
    }

    override fun onItemDrop(view: View, pos: Int, data: Any) {
        val subAccount = data as SubAccount
        val args = Bundle()
        args.putParcelable(SubAccount::class.simpleName, subAccount)
        subAccount.accountType?.let {
            when (it) {
                AccountType.B2C_HOUSEHOLD.name -> //showRequestDeclinedPopup(subAccount)
                    navigateForwardWithAnimation(
                        SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment(),
                        args
                    )
            }
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)

    }

    override fun onItemExited(view: View) {
        view.background = context?.getDrawable(R.drawable.card_border_container)
    }

    override fun onItemEntered(view: View) {
        view.background = context?.getDrawable(R.drawable.bg_gray_rounded_border)
    }

    override fun onItemLongClick(view: View, pos: Int, id: Long, data: Any): Boolean? {
        if (pos == 0) {
            return startDrag(view)
        }
        return true
    }

    private fun showRequestDeclinedPopup(data: SubAccount) {
        confirm(getString(R.string.screen_house_hold_sub_account_declined_by_popup_message),
            getString(
                R.string.screen_house_hold_sub_account_declined_by_popup_title,
                data.firstName
            ), getString(R.string.screen_house_hold_sub_account_popup_resend_button_text),
            getString(R.string.screen_house_hold_sub_account_popup_remove_refund_button_text),
            callback = { viewModel.resendRequestToHouseHoldUser(data) },// "uuid" : "26287f84-5f9c-4bfe-b8ab-e8016cc7b23d",  "uuid" : "b4ba4040-d904-4742-96aa-374ce6ed6112",
            negativeCallback = { viewModel.RemoveRefundHouseHoldUser(data) })
    }

    private fun showIneligiblePopup(data: SubAccount) {
        alert(
            getString(
                R.string.screen_house_hold_sub_account_ineligible_popup_message,
                data.firstName
            ),
            getString(
                R.string.screen_house_hold_sub_account_ineligible_popup_title,
                data.firstName
            ),
            getString(R.string.screen_house_hold_sub_account_popup_remove_refund_button_text)
        ) { viewModel.RemoveRefundHouseHoldUser(data) }
    }

    class Adapter(mValue: MutableList<SubAccount>, navigation: NavController?) :
        BaseRVAdapter<SubAccount, SubAccountCardItemVM, BaseViewHolder<SubAccount, SubAccountCardItemVM>>(
            mValue, navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = SubAccountCardItemVM()
        override fun getVariableId() = BR.subAccountCardItemVm
    }

}