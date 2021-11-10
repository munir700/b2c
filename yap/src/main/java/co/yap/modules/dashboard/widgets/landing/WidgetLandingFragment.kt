package co.yap.modules.dashboard.widgets.landing

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.SpacesItemDecoration
import co.yap.widgets.advrecyclerview.animator.DraggableItemAnimator
import co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager
import co.yap.widgets.advrecyclerview.swipeable.RecyclerViewSwipeManager
import co.yap.widgets.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.bottomsheet_edit_widget.BottomSheetEditWidget
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
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
        handleClick()
    }

    private fun handleClick() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.switchWidget -> {
                    openBottomSheet()
                }
            }
        })
    }

    private fun openBottomSheet() {
        var onBottomSheetButtonClick: View.OnClickListener?
        var widgetBottomSheet = BottomSheetEditWidget(BottomSheetConfiguration())
        activity?.supportFragmentManager.let { fragmentManager ->
            onBottomSheetButtonClick = View.OnClickListener { view ->
                when (view.id) {
                    R.id.btnHide -> {
                        val intent = Intent()
                        intent.putExtra("ACTION", Constants.HIDE_WIDGET)
                        activity?.setResult(RESULT_OK, intent)
                        activity?.finish()

                    }
                    R.id.tvCancel -> {
                        widgetBottomSheet.dismiss()
                        switchWidget.isChecked = false
                    }
                }
            }
            widgetBottomSheet =
                BottomSheetEditWidget(
                    buttonClickListener = onBottomSheetButtonClick,
                    configuration = BottomSheetConfiguration(
                        heading = context?.let {
                            Translator.getString(
                                it,
                                Strings.screen_dashboard_widget_edit_hide_bottom_sheet_title
                            )
                        }, subHeading = context?.let {
                            Translator.getString(
                                it,
                                Strings.screen_dashboard_widget_edit_hide_bottom_sheet_content
                            )
                        }
                    )
                )
            fragmentManager?.let { widgetBottomSheet.show(it, "") }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.widgetDataList.addAll(viewModel.parentViewModel!!.widgetDataList)
        mAdapter = WidgetAdapter(mutableListOf(), null)
        viewModel.widgetAdapter.set(mAdapter)
        viewModel.filterWidgetDataList()
        initDragDropAdapter()
        viewModel.apiSuccessEvent.observe(this, apiSuccessObserver)

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
            setInitiateOnLongPress(true)
//            setInitiateOnMove(false)
            setLongPressTimeout(1000)
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
        viewModel.changeStatus( positionFrom = position, positionTo = 0, status = false, isDragDrop = false)
    }

    override fun onAddedButtonClick(v: View?, position: Int) {
        viewModel.changeStatus( positionFrom = position, positionTo = 0, status = true, isDragDrop = false)
    }

    override fun onDragDropFinished( positionFrom: Int, positionTo: Int) {
        viewModel.changeStatus( positionFrom = positionFrom, positionTo = positionTo, status = true, isDragDrop = true)
    }

    override fun onItemDragStarted(position: Int) {
    }

    override fun onItemDragPositionChanged(
        fromPosition: Int,
        toPosition: Int
    ) {
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
    }

    override fun onItemDragMoveDistanceUpdated(offsetX: Int, offsetY: Int) {
    }

    override fun onItemSwipeStarted(position: Int) {
    }

    override fun onItemSwipeFinished(position: Int, result: Int, afterSwipeReaction: Int) {
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

    override fun onDestroyView() {
        viewModel.apiSuccessEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val apiSuccessObserver = Observer<Boolean> {
        if( it){
            setResultData()
        }else{
            activity?.finish()
        }
    }

    override fun onBackPressed(): Boolean {
        viewModel.requestWidgetUpdation()
        return true
    }

    private fun setResultData() {
        val intent = Intent()
        intent.putExtra("ACTION", Constants.CHANGE_WIDGET)
        activity?.setResult(RESULT_OK, intent)
        activity?.finish()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                viewModel.requestWidgetUpdation()
            }
        }
    }
}