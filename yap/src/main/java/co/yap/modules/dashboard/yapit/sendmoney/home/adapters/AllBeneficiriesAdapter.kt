package co.yap.modules.dashboard.yapit.sendmoney.home.adapters

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBeneficiariesBinding
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.widgets.swipe_lib.SwipeCallBack
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class AllBeneficiriesAdapter(
    private val list: MutableList<Beneficiary>,
    var swipeCallBack: SwipeCallBack
) :
    BaseBindingRecyclerAdapter<Beneficiary, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_beneficiaries

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return AllBeneficiariesItemViewHolder(binding as ItemBeneficiariesBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is AllBeneficiariesItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener,swipeCallBack)
        }
    }


    class AllBeneficiariesItemViewHolder(
        private val itemContactsBinding: ItemBeneficiariesBinding) :
        RecyclerView.ViewHolder(itemContactsBinding.root) {

        fun onBind(
            beneficiary: Beneficiary?,
            position: Int,
            onItemClickListener: OnItemClickListener?,
            onSwipe: SwipeCallBack
        ) {

            itemContactsBinding.tvNameInitials.background = Utils.getContactBackground(
                itemContactsBinding.tvNameInitials.context,
                position
            )

            itemContactsBinding.tvNameInitials.setTextColor(
                Utils.getContactColors(
                    itemContactsBinding.tvNameInitials.context, position
                )
            )

            itemContactsBinding.viewModel = BeneficiaryItemViewModel(beneficiary, position, onItemClickListener)
            itemContactsBinding.executePendingBindings()

            itemContactsBinding.btnDelete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onSwipe.onSwipeDelete(beneficiary!!,position)
                }
            })

            itemContactsBinding.btnEdit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onSwipe.onSwipeEdit(beneficiary!!)
                }
            })
        }
    }

}
