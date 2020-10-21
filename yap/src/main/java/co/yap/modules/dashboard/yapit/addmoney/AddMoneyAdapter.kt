package co.yap.modules.dashboard.yapit.addmoney

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapItAddMoneyBinding
import co.yap.databinding.ItemYapItAddMoneyGooglePayBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class AddMoneyAdapter(context: Context, private val list: MutableList<AddMoneyOptions>) :
    BaseBindingRecyclerAdapter<AddMoneyOptions, RecyclerView.ViewHolder>(list) {

    private val type1 = 1
    private val type2 = 2

    private var dimensions: IntArray = Utils.getCardDimensions(context, 50, 45)
    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == type1) R.layout.item_yap_it_add_money else R.layout.item_yap_it_add_money_google_pay

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemYapItAddMoneyBinding) ViewHolder(binding) else ViewHolderType2(
            binding as ItemYapItAddMoneyGooglePayBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ViewHolder) {
            holder.onBind(list[position], position, dimensions, onItemClickListener)
        } else if (holder is ViewHolderType2) {
            holder.onBind(list[position], position, dimensions, onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == Constants.ADD_MONEY_GOOGLE_PAY || list[position].id == Constants.ADD_MONEY_SAMSUNG_PAY) type2 else type1
    }

    class ViewHolder(private val itemYapItAddMoneyBinding: ItemYapItAddMoneyBinding) :
        RecyclerView.ViewHolder(itemYapItAddMoneyBinding.root) {
        fun onBind(
            addMoneyOptions: AddMoneyOptions,
            position: Int,
            dimensions: IntArray,
            onItemClickListener: OnItemClickListener?
        ) {
            itemYapItAddMoneyBinding.viewModel =
                YapItAddMoneyItemVM(
                    addMoneyOptions,
                    position,
                    onItemClickListener
                )
            itemYapItAddMoneyBinding.executePendingBindings()
        }
    }

    class ViewHolderType2(private val itemYapItAddMoneyGooglePayBinding: ItemYapItAddMoneyGooglePayBinding) :
        RecyclerView.ViewHolder(itemYapItAddMoneyGooglePayBinding.root) {

        fun onBind(
            addMoneyOptions: AddMoneyOptions,
            position: Int,
            dimensions: IntArray,
            onItemClickListener: OnItemClickListener?
        ) {

            itemYapItAddMoneyGooglePayBinding.viewModel =
                YapItAddMoneyItemVM(
                    addMoneyOptions,
                    position,
                    onItemClickListener
                )
            itemYapItAddMoneyGooglePayBinding.executePendingBindings()
        }
    }
}

