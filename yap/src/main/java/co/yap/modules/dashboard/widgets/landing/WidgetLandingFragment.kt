package co.yap.modules.dashboard.widgets.landing

import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.widgets.SpacesItemDecoration
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.swipeable.RecyclerViewSwipeManager
import co.yap.widgets.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.fragment_widget_landing.*

class WidgetLandingFragment : BaseBindingFragment<IWidgetLanding.ViewModel>(),
    IWidgetLanding.View,
    RecyclerViewDragDropManager.OnItemDragEventListener,
    RecyclerViewSwipeManager.OnItemSwipeEventListener, WidgetAdapter.EventListener {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null
    private var mRecyclerViewSwipeManager: RecyclerViewSwipeManager? = null
    private var mRecyclerViewTouchActionGuardManager: RecyclerViewTouchActionGuardManager? = null
    private lateinit var mAdapter: WidgetAdapter

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_widget_landing

    override val viewModel: WidgetLandingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.parentViewModel =
            activity?.let { ViewModelProviders.of(it).get(WidgetViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.widgetDataList.addAll(viewModel.parentViewModel!!.widgetDataList)
        mAdapter = WidgetAdapter(mutableListOf(), null)
        viewModel.widgetAdapter?.set(mAdapter)
        viewModel.filterWidgetDataList()
        initDragDropAdapter()
    }

    private fun initDragDropAdapter() {
        // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        mRecyclerViewTouchActionGuardManager = RecyclerViewTouchActionGuardManager().apply {
            setInterceptVerticalScrollingWhileAnimationRunning(true)
            isEnabled = true
        }
        mAdapter.setEventListener(this)
        mRecyclerViewDragDropManager = RecyclerViewDragDropManager().apply {
            mRecyclerViewSwipeManager = RecyclerViewSwipeManager()
            mWrappedAdapter = createWrappedAdapter(mAdapter)
            mWrappedAdapter?.let {
                mWrappedAdapter = mRecyclerViewSwipeManager?.createWrappedAdapter(it)
            }
            mRecyclerViewSwipeManager?.onItemSwipeEventListener = this@WidgetLandingFragment
//            setInitiateOnLongPress(true)
//            setInitiateOnMove(true)
//            setLongPressTimeout(250)
            dragEdgeScrollSpeed = 1.0f
            setDraggingItemShadowDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.material_shadow_z3
                ) as NinePatchDrawable
            )
//            dragStartItemAnimationDuration = 2000
            draggingItemAlpha = 1f
            isCheckCanDropEnabled = false
//            draggingItemRotation = 15.0f
            onItemDragEventListener = this@WidgetLandingFragment
//            itemSettleBackIntoPlaceAnimationDuration = 2000
            itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
        }
        recyclerView?.apply {
            val animator = DraggableItemAnimator()
            // Change animations are enabled by default since support-v7-recyclerview v22.
            // Disable the change animation in order to make turning back animation of swiped item works properly.
            animator.supportsChangeAnimations = false
            adapter = mWrappedAdapter // requires *wrapped* adapter
            itemAnimator = animator
            addItemDecoration(
                SpacesItemDecoration(
                    0,
                    0,
                    dimen(R.dimen.margin_extra_small),
                    dimen(R.dimen.margin_extra_small),
                    true
                )
            )
            mRecyclerViewTouchActionGuardManager?.attachRecyclerView(this)
            mRecyclerViewSwipeManager?.attachRecyclerView(this)
            mRecyclerViewDragDropManager?.attachRecyclerView(this)
        }
    }

    override fun onItemPinned(position: Int, isPinned: Boolean) {

    }

    override fun onUnderSwipeableViewButtonClicked(v: View?, position: Int) {
        Log.v("", "")
        /*confirm(
            message = getString(screen_multi_currency_wallet_display_text_delete_message),
            title = getString(screen_multi_currency_wallet_display_text_delete_header)
        )*/
        viewModel.changeStatus( position = position, status = false)
//        mAdapter.removeAt(position)
    }

    override fun onAddedButtonClick(v: View?, position: Int) {
        viewModel.changeStatus( position = position, status = true)
    }

    private fun onClick(id: Int) {
        when (id) {
//            R.id.tvEdit -> {
//                viewModel.state.editWallet.value = !viewModel.state.editWallet.value!!
//                mAdpter.editWallet = viewModel.state.editWallet.value!!
//                tvEdit?.text = if (mAdpter.editWallet) getString(
//                    screen_multi_currency_wallet_display_text_close_wallet
//                ) else getString(screen_multi_currency_wallet_display_text_edit_wallet)
//            }
        }
    }

    override fun onItemDragStarted(position: Int) {

    }

    override fun onItemDragPositionChanged(
        fromPosition: Int,
        toPosition: Int
    ) {
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        mAdapter.editWidget = false
    }

    override fun onItemDragMoveDistanceUpdated(offsetX: Int, offsetY: Int) {
    }

    override fun onItemSwipeStarted(position: Int) {
        Log.d("", "")
    }

    override fun onItemSwipeFinished(position: Int, result: Int, afterSwipeReaction: Int) {
        Log.d("", "")
    }

    override fun onPause() {
        mRecyclerViewDragDropManager?.cancelDrag()
        super.onPause()
    }

    override fun onDestroy() {
        mRecyclerViewDragDropManager?.let {
            it.release()
            mRecyclerViewDragDropManager = null
        }
        mRecyclerViewSwipeManager?.let {
            it.release()
            mRecyclerViewSwipeManager = null
        }
        mRecyclerViewTouchActionGuardManager?.let {
            it.release()
            mRecyclerViewTouchActionGuardManager = null
        }
        mWrappedAdapter?.let {
            WrapperAdapterUtils.releaseAll(it)
            mWrappedAdapter = null
        }
        super.onDestroy()
    }
}