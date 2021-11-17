package co.yap.modules.dashboard.widgets

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.networking.customers.models.dashboardwidget.WidgetData
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
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.fragment_widget.*


class WidgetFragment : BaseBindingFragment<IWidget.ViewModel>(),
    IWidget.View,
    RecyclerViewDragDropManager.OnItemDragEventListener,
    RecyclerViewSwipeManager.OnItemSwipeEventListener, WidgetAdapter.EventListener {
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null
    private var mRecyclerViewSwipeManager: RecyclerViewSwipeManager? = null
    private var mRecyclerViewTouchActionGuardManager: RecyclerViewTouchActionGuardManager? = null
    private lateinit var mAdapter: WidgetAdapter
    var shardPrefs: SharedPreferenceManager? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_widget

    override val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleClick()
        shardPrefs = SharedPreferenceManager.getInstance(requireContext())
    }

    private fun handleClick() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.switchWidget -> {
                    if (switchWidget.isChecked) {
                        openBottomSheet()
                    } else {
                        shardPrefs?.save(Constants.WIDGET_HIDDEN_STATUS, false)
                        viewModel.state.isVisibilityChange.set(true)
                    }
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
                        viewModel.state.isVisibilityChange.set(true)
                        shardPrefs?.save(
                            Constants.WIDGET_HIDDEN_STATUS,
                            true
                        )
                        setResultData()
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
        getDataFromArguments()
        initRecycleView()
        initDragDropAdapter()
        viewModel.apiSuccessEvent.observe(this, apiSuccessObserver)
        shardPrefs?.let { pref ->
            switchWidget.isChecked = pref.getValueBoolien(Constants.WIDGET_HIDDEN_STATUS, false)
        }

        val callback = object : OnBackPressedCallback(
            true
            /** true means that the callback is enabled */
        ) {
            override fun handleOnBackPressed() {
                viewModel.requestWidgetUpdation()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun getDataFromArguments() {
        arguments?.let {
            it.containsKey(Constants.WIDGET_LIST).let { isExist ->
                if (isExist) {
                    it.getParcelableArrayList<WidgetData>(Constants.WIDGET_LIST)?.let { list ->
                        viewModel.widgetDataList.addAll(list)
                    }
                }
            }
        }
    }

    private fun initRecycleView() {
        mAdapter = WidgetAdapter(mutableListOf(), null)
        viewModel.widgetAdapter.set(mAdapter)
        viewModel.filterWidgetDataList()
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
            mRecyclerViewSwipeManager?.onItemSwipeEventListener = this@WidgetFragment
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
            onItemDragEventListener = this@WidgetFragment
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
        viewModel.changeStatus(
            positionFrom = position,
            positionTo = viewModel.widgetDataList.size - 1,
            status = false,
            isDragDrop = false
        )
    }

    override fun onAddedButtonClick(v: View?, position: Int) {
        viewModel.changeStatus(
            positionFrom = position,
            positionTo = viewModel.getCountOfStatusFromWidgetDataList(),
            status = true,
            isDragDrop = false
        )
    }


    override fun onItemDragStarted(position: Int) {
    }

    override fun onItemDragPositionChanged(
        fromPosition: Int,
        toPosition: Int
    ) {
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        viewModel.changeStatus(
            positionFrom = fromPosition,
            positionTo = toPosition,
            status = true,
            isDragDrop = true
        )
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
        setResultData()
    }

//    override fun onBackPressed(): Boolean {
//        viewModel.requestWidgetUpdation()
//        return true
//    }

    private fun setResultData() {
        val intent = Intent()
        intent.putExtra("ACTION", viewModel.getWidgetShuffledList().isNotEmpty())
        intent.putExtra("HIDE_WIDGET", viewModel.state.isVisibilityChange.get())
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