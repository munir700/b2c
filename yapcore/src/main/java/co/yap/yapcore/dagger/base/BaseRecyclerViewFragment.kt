package co.yap.yapcore.dagger.base


import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.yap.networking.models.ApiResponse
import co.yap.widgets.*
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.R
import co.yap.yapcore.dagger.base.interfaces.UiRefreshable
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.helpers.extentions.bindView
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.interfaces.OnItemClickListener
import javax.inject.Inject


/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseRecyclerViewFragment<VB : ViewDataBinding, S : IBase.State, VM : BaseRecyclerAdapterVM<T, S>,
        A : BaseRVAdapter<T, *, *>, T : ApiResponse>
    : BaseNavViewModelFragment<VB, S, VM>(), MultiStateView.OnReloadListener, UiRefreshable,
    OnItemClickListener {

    @Inject
    protected lateinit var adapter: A
    protected var isRefreshing: Boolean = false

    val recyclerView: RecyclerView? by bindView(R.id.recyclerView)
    private val refreshLayout: SwipeRefreshLayout? by bindView(R.id.refreshLayout)
    val stateLayout: MultiStateView? by bindView(R.id.multiStateView)

    /**
     * if return false in child fragment then child fragment should need implement owen [RecyclerView]
     */
    var setupRecyclerView = true
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        if (setupRecyclerView) {
            getmViewModel().adapter.set(adapter)
            // if (recyclerView?.layoutManager is GridLayoutManager) {
            recyclerView?.addItemDecoration(getItemDecoration())

            stateLayout?.setOnReloadListener(this)
            refreshLayout?.setColorSchemeResources(
                R.color.colorPrimaryDark, R.color.colorPrimary,
                R.color.colorPrimaryAlt, R.color.colorDisabledBtn
            )
            refreshLayout?.setOnRefreshListener { refresh() }
            adapter.onItemClickListener = this
            viewModel.stateLiveData.observe(
                this,
                Observer { if (it.status != Status.IDEAL) handleState(it) })
        }
    }

    override fun onReload(view: View) {
        refreshWithUi()
    }

    open fun getItemDecoration(): RecyclerView.ItemDecoration {
        return if (recyclerView?.layoutManager is GridLayoutManager)
            SpaceGridItemDecoration(dimen(R.dimen.margin_normal)!!, 2, true)
        else SpacesItemDecoration(dimen(R.dimen.margin_normal)!!, true)
    }

    fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> stateLayout?.viewState = MultiStateView.ViewState.LOADING
            Status.ERROR -> stateLayout?.viewState = MultiStateView.ViewState.ERROR
            Status.SUCCESS -> stateLayout?.viewState =
                if (adapter.datas.count() > 0) MultiStateView.ViewState.CONTENT else MultiStateView.ViewState.EMPTY
            else -> stateLayout?.viewState = MultiStateView.ViewState.EMPTY

        }
    }

    override fun doneRefresh() {
        refreshLayout?.isRefreshing = false
        isRefreshing = false
    }

    override fun refreshWithUi() {
        refreshWithUi(0)
    }

    override fun refreshWithUi(delay: Int) {
        refreshLayout?.run {
            postDelayed({
                refreshUi()
                refresh()
            }, delay.toLong())
        }
    }

    override fun setRefreshEnabled(enabled: Boolean) {
        refreshLayout?.isEnabled = enabled
    }

    override fun refresh() {
        if (!isRefreshing) {
            getmViewModel().refresh()
            isRefreshing = true
        }

        refreshLayout?.postDelayed({
            doneRefresh()
        }, 500)
    }

    private fun refreshUi() {
        refreshLayout?.isRefreshing = true

    }

    override fun onItemClick(view: View, data: Any, pos: Int) {

    }
}