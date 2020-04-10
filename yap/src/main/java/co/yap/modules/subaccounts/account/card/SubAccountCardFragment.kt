package co.yap.modules.subaccounts.account.card

import android.animation.Animator
import android.graphics.Color
import android.view.*
import android.view.animation.TranslateAnimation
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
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.item_sub_account_card.*
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
        setRefreshEnabled(false)
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
       /* val subAccount = data as SubAccount
        subAccount.accountType?.let {
            startFragment(HHSalaryProfileFragment::class.java.name)
//            navigateForwardWithAnimation(SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment())
        }
            ?: launchActivity<HouseHoldLandingActivity>(requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD)
*/
        if(pos == 0) {
            swipeViews(true)
        }
    }

    private fun swipeViews(swipe: Boolean) {
        if(swipe){
            layout_image.visibility = View.GONE
            llBankTransferType.background = resources.getDrawable(R.drawable.card_border_selected)
            layout_swipe_image.visibility = View.VISIBLE
            animate(layout_swipe_image)
        }else{
            layout_image.visibility = View.VISIBLE
            layout_swipe_image.visibility = View.GONE
            llBankTransferType.background = resources.getDrawable(R.drawable.card_border_selector)
        }
    }

    private fun animate(view: View){
        YoYo.with(Techniques.SlideInDown)
            .duration(1000)
            .repeat(0)
            .playOn(view)
    }

    override fun onItemDrag(view: View, pos: Int, event: DragEvent, data: Any): Boolean? {
        swipeViews(false)
        return dragAndDropManager?.onItemDrag(view, pos, event, data)
    }

    override fun onItemLongClick(view: View, pos: Int, id: Long, data: Any): Boolean? {
        if (pos == 0) {
            return dragAndDropManager?.startDrag(view)
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

    override fun onItemDrop(view: View, pos: Int, data: Any) {
        val subAccount = data as SubAccount
        subAccount.accountType?.let {
            startFragment(HHSalaryProfileFragment::class.java.name)
        }
    }

}