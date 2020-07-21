package co.yap.household.helpers

import android.graphics.PorterDuff
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.ThemeColorUtils
import co.yap.yapcore.transactions.TransactionRecyclerView
import co.yap.yapcore.transactions.TransactionsAdapter
@Deprecated("")
object TransactionBindingAdapter {
    @BindingAdapter("txnList")
    @JvmStatic
    fun setViewContainerAsLinearLayout(
        recyclerView: TransactionRecyclerView,
        list: ObservableField<MutableList<HomeTransactionListData>>
    ) {
        if (true == getRecycleViewAdaptor(recyclerView.rvTransaction)?.isLoaderMore?.value && null != list.get()) {
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.isLoaderMore?.value = false
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.itemCount?.let {
                getRecycleViewAdaptor(recyclerView.rvTransaction)?.notifyItemRemoved(it)
            }
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.addList(
                list.get() ?: mutableListOf()
            )
        } else {
            if (!list.get().isNullOrEmpty()) {
                getRecycleViewAdaptor(recyclerView.rvTransaction)?.setList(
                    list.get() ?: mutableListOf()
                )
            }
        }
    }

    private fun getRecycleViewAdaptor(recyclerView: RecyclerView?): TransactionsAdapter? {
        return recyclerView?.adapter as? TransactionsAdapter
    }



    @JvmStatic
    @BindingAdapter("yapItIconTint")
    fun setYapItIconTint(view: com.google.android.material.floatingactionbutton.FloatingActionButton, isHouseHold: Boolean) {

        if (SharedPreferenceManager(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {

            view.setColorFilter(
                view.context.getColor(R.color.colorPrimaryHouseHold),
                PorterDuff.Mode.SRC_ATOP
            )

        }
    }
}