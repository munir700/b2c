package co.yap.yapcore.dagger.base


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
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.helpers.extentions.bindView
import co.yap.yapcore.helpers.extentions.dimen
import javax.inject.Inject


/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseRecyclerViewFragment<VB : ViewDataBinding, S : IBase.State, VM : BaseRecyclerAdapterVM<T, S>,
        A : BaseRVAdapter<T, *, *>, T : ApiResponse>
    : BaseViewModelFragment<VB, S, VM>(), MultiStateView.OnReloadListener {

    @Inject
    protected lateinit var adapter: A

    val recyclerView: RecyclerView? by bindView(R.id.recyclerView)
    private val refreshLayout: SwipeRefreshLayout? by bindView(R.id.refreshLayout)
    val stateLayout: MultiStateView? by bindView(R.id.multiStateView)

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        if (setupRecyclerView()) {
            getmViewModel().adapter.set(adapter)
            //recyclerView?.adapter = getmViewModel().adapter.get()
            if (recyclerView?.layoutManager is GridLayoutManager) {
                recyclerView?.addItemDecoration(
                    SpaceGridItemDecoration(dimen(R.dimen.margin_normal)!!, 2, true)
                )
            } else {
                recyclerView?.addItemDecoration(spacesItemDecoration())
            }
        }
        stateLayout?.setOnReloadListener(this)
        viewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })

    }

    open fun setupRecyclerView() = true
    open fun spacesItemDecoration() =
        SpacesItemDecoration(dimen(R.dimen.margin_normal)!!, true)

    protected fun spacesItemDecoration(resource: Int) =
        SpacesItemDecoration(dimen(resource)!!, true)

    fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> stateLayout?.viewState = MultiStateView.ViewState.LOADING
            Status.ERROR -> stateLayout?.viewState = MultiStateView.ViewState.ERROR
            Status.SUCCESS -> stateLayout?.viewState =
                if (adapter.datas.count() > 0) MultiStateView.ViewState.CONTENT else MultiStateView.ViewState.EMPTY
            else -> stateLayout?.viewState = MultiStateView.ViewState.EMPTY

        }
    }
}