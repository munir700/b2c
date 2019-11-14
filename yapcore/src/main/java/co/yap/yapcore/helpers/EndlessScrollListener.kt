package co.yap.yapcore.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener {
    private var visibleThreshold = 1
    private var currentPage = 1
    private var loading = false

    constructor(visibleThreshold: Int) {
        this.visibleThreshold = visibleThreshold
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        if (!loading && (layoutManager.itemCount - lastVisiblePosition) <= (visibleThreshold)) {
            // If it isn't currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more dataList.
            // If we do need to reload some more dataList, we execute onLoadMore to fetch the dataList.
            currentPage++
            loading = onLoadMore(currentPage)
        }
    }

    // Defines the process for actually loading more dataList based on page
    // Returns true if more dataList is being loaded; returns false if there is no more dataList to load.
    abstract fun onLoadMore(page: Int): Boolean
}