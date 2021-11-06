package co.yap.modules.dashboard.widgets

import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.home.models.WidgetItemList
import co.yap.widgets.SpacesItemDecoration
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.swipeable.RecyclerViewSwipeManager
import co.yap.widgets.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), IWidget.View,
    RecyclerViewDragDropManager.OnItemDragEventListener,
    RecyclerViewSwipeManager.OnItemSwipeEventListener, WidgetAdapter.EventListener {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null
    private var mRecyclerViewSwipeManager: RecyclerViewSwipeManager? = null
    private var mRecyclerViewTouchActionGuardManager: RecyclerViewTouchActionGuardManager? = null
    private lateinit var mAdapter: WidgetAdapter

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDatFromBundle()
        intRecyclersView()
    }

    private fun getDatFromBundle() {
        if (intent?.hasExtra(ExtraKeys.EDIT_WIDGET.name) == true) {
            intent.getParcelableExtra<WidgetItemList>(ExtraKeys.EDIT_WIDGET.name)?.let {
                viewModel.widgetDataList.addAll(it.widgetData)
            }
        }
    }

    private fun intRecyclersView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@WidgetActivity)
            mAdapter = WidgetAdapter(mutableListOf(), null)
            adapter = mAdapter
            viewModel.widgetAdapter?.set(mAdapter)
            viewModel.filterWidgetDataList()
            recyclerView.setHasFixedSize(true)
        }
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
            mRecyclerViewSwipeManager?.onItemSwipeEventListener = this@WidgetActivity
//            setInitiateOnLongPress(true)
//            setInitiateOnMove(true)
//            setLongPressTimeout(250)
            dragEdgeScrollSpeed = 1.0f
            setDraggingItemShadowDrawable(
                ContextCompat.getDrawable(
                    this@WidgetActivity,
                    R.drawable.flag_id
                ) as NinePatchDrawable
            )
//            dragStartItemAnimationDuration = 2000
            draggingItemAlpha = 1f
            isCheckCanDropEnabled = true
//            draggingItemRotation = 15.0f
            onItemDragEventListener = this@WidgetActivity
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
            mRecyclerViewDragDropManager?.attachRecyclerView(this)
            mRecyclerViewTouchActionGuardManager?.attachRecyclerView(this)
            mRecyclerViewSwipeManager?.attachRecyclerView(this)
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }

    override fun onItemDragStarted(position: Int) {
    }

    override fun onItemDragPositionChanged(fromPosition: Int, toPosition: Int) {
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
    }

    override fun onItemDragMoveDistanceUpdated(offsetX: Int, offsetY: Int) {
    }

    override fun onItemSwipeStarted(position: Int) {
    }

    override fun onItemSwipeFinished(position: Int, result: Int, afterSwipeReaction: Int) {
    }

    override fun onItemPinned(position: Int, isPinned: Boolean) {
    }

    override fun onUnderSwipeableViewButtonClicked(v: View?, position: Int) {
        /*confirm(
            message = getString(screen_multi_currency_wallet_display_text_delete_message),
            title = getString(screen_multi_currency_wallet_display_text_delete_header)
        ) {*/
            mAdapter.removeAt(position)
//        }
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