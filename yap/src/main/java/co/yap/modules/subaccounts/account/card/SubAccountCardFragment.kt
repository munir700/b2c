package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.dashboard.store.household.activities.HouseHoldLandingActivity
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragmentDirections
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.launchActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.item_sub_account_card.*


class SubAccountCardFragment :
    BaseRecyclerViewFragment<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountAdapter, SubAccount>(), RecyclerViewDragDropManager.OnItemDragEventListener {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null

    override fun getBindingVariable() = BR.subAccountCardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_card
    override fun postExecutePendingBindings() {
        setRefreshEnabled(false)
        super.postExecutePendingBindings()
        setHasOptionsMenu(true)
        initDragDropAdapter()
    }

    override fun handleState(state: State?) {
        super.handleState(state)
        recyclerView?.adapter = mWrappedAdapter
    }

    private fun initDragDropAdapter() {
            mRecyclerViewDragDropManager = RecyclerViewDragDropManager().apply {
            mWrappedAdapter = createWrappedAdapter(adapter)
            setInitiateOnLongPress(false)
            setInitiateOnMove(true)
            setLongPressTimeout(750)
            dragEdgeScrollSpeed = 1.0f
            dragStartItemAnimationDuration = 250
            draggingItemAlpha = 1f
            isCheckCanDropEnabled = true
            draggingItemRotation = 15.0f
            onItemDragEventListener = this@SubAccountCardFragment
            itemSettleBackIntoPlaceAnimationDuration = 1000
            itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
        }
        val animator = DraggableItemAnimator()
        recyclerView?.adapter = mWrappedAdapter // requires *wrapped* adapter
        recyclerView?.itemAnimator = animator
        recyclerView?.let { mRecyclerViewDragDropManager?.attachRecyclerView(it) }
    }

    override fun onItemDragStarted(position: Int) {
    }

    override fun onItemDragPositionChanged(
        fromPosition: Int,
        toPosition: Int,
        draggingItemHolder: RecyclerView.ViewHolder?,
        swapTargetHolder: RecyclerView.ViewHolder?
    ) {
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        val subAccount = adapter.getData()[toPosition]
        val args = Bundle()
        args.putParcelable(SubAccount::class.simpleName, subAccount)
        subAccount.accountType?.let {
            when (it) {
                AccountType.B2C_HOUSEHOLD.name -> {
                    navigateForwardWithAnimation(
                        SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHIbanSendMoneyFragment(),
                        args
                    )
                }
            }
        }
    }

    override fun onItemDragMoveDistanceUpdated(offsetX: Int, offsetY: Int) {
    }

    override fun onPause() {
        mRecyclerViewDragDropManager?.cancelDrag()
        super.onPause()
    }

    override fun onDestroyView() {
        mRecyclerViewDragDropManager?.let {
            it.release()
            mRecyclerViewDragDropManager = null
        }

        mWrappedAdapter?.let {
            WrapperAdapterUtils.releaseAll(it)
            mWrappedAdapter = null
        }
        super.onDestroyView()
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
        val subAccount = data as SubAccount
        val args = Bundle()
        args.putParcelable(SubAccount::class.simpleName, subAccount)
        subAccount.accountType?.let {
            when (it) {
                AccountType.B2C_ACCOUNT.name -> swipeViews(true)
                AccountType.B2C_HOUSEHOLD.name -> {
                    if (subAccount.cardStatus == PartnerBankStatus.REJECTED.status)
                        showIneligiblePopup(subAccount)
                    else if (subAccount.pinCreated == true || subAccount.salaryTransferred == true)
                        navigateForwardWithAnimation(
                            SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment(),
                            args
                        )
                    else showRequestDeclinedPopup(subAccount)
                }
            }
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)
    }


    private fun swipeViews(swipe: Boolean) {
        if (swipe) {
            imgProfile.visibility = View.INVISIBLE
            layout_swipe_image.visibility = View.VISIBLE
            tv_drag_and_drop_label.visibility = View.VISIBLE
            YoYo.with(Techniques.SlideInDown).duration(400).repeat(0).playOn(layout_swipe_image)
            YoYo.with(Techniques.SlideInDown).duration(400).repeat(0).playOn(tv_drag_and_drop_label)
        } else {
            imgProfile.visibility = View.VISIBLE
            layout_swipe_image.visibility = View.INVISIBLE
            tv_drag_and_drop_label.visibility = View.INVISIBLE
        }
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

}