package co.yap.modules.subaccounts.account.card

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragmentDirections
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.Utils.setLightStatusBar
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.hilt.base.fragment.BaseRecyclerViewFragmentV2
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_sub_account_card.*


@AndroidEntryPoint
class SubAccountCardFragment :
    BaseRecyclerViewFragmentV2<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountAdapter, SubAccount>(), RecyclerViewDragDropManager.OnItemDragEventListener {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    override val viewModel: SubAccountCardVM by viewModels()
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null
    override fun getBindingVariable() = BR.subAccountCardVM
    override fun getLayoutId() = R.layout.fragment_sub_account_card
    override fun preInit() {
        super.preInit()
        setLightStatusBar(requireActivity() , Color.WHITE )
    }
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setRefreshEnabled(false)
        setHasOptionsMenu(true)
        initDragDropAdapter()
    }

    override fun onClick(id: Int) {
    }

    override fun onItemDragPositionChanged(fromPosition: Int, toPosition: Int) {
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
            setLongPressTimeout(250)
            dragEdgeScrollSpeed = 1.0f
            dragStartItemAnimationDuration = 750
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

//    override fun onItemDragPositionChanged(
//        fromPosition: Int,
//        toPosition: Int,
//        draggingItemHolder: RecyclerView.ViewHolder?,
//        swapTargetHolder: RecyclerView.ViewHolder?
//    ) {
//    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        val subAccount = adapter.getData()[toPosition]
        subAccount.accountType?.let {
            when (it) {
                AccountType.B2C_HOUSEHOLD.name -> {
                    navigateForwardWithAnimation(
                        SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHIbanSendMoneyFragment(),
                        bundleOf(SubAccount::class.java.simpleName to subAccount)
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
        launchActivity<NavHostPresenterActivity> {
            putExtra(NAVIGATION_Graph_ID, R.navigation.add_house_hold_user_navigation)
            putExtra(NAVIGATION_Graph_START_DESTINATION_ID, R.id.houseHoldLandingFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        val subAccount = data as SubAccount
        val args = Bundle()
        args.putParcelable(SubAccount::class.java.simpleName, subAccount)
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
                    else if (subAccount.cardStatus?.contains("Declined by") == true) showRequestDeclinedPopup(subAccount)
                }
            }
        } ?: launchActivity<NavHostPresenterActivity> {
            putExtra(NAVIGATION_Graph_ID, R.navigation.add_house_hold_user_navigation)
            putExtra(NAVIGATION_Graph_START_DESTINATION_ID, R.id.houseHoldLandingFragment)
        }
    }


    private fun swipeViews(swipe: Boolean) {
        if (swipe) {
            imgProfile.visibility = View.INVISIBLE
            tv_drag_and_drop_label.visibility = View.VISIBLE
            YoYo.with(Techniques.SlideInDown).duration(400).repeat(0).playOn(tv_drag_and_drop_label)
        } else {
            imgProfile.visibility = View.VISIBLE
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