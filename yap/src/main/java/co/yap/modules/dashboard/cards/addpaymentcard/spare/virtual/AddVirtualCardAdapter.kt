package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapCardEmptyBinding
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class AddVirtualCardAdapter(
    private val list: MutableList<HomeTransactionListData>,
    private val adaptorClick: OnItemClickListener
) :
    BaseBindingRecyclerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getLayoutIdForViewType(viewType: Int): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    class ViewHolder(private val itemYapCardEmptyBinding: ItemYapCardEmptyBinding) :
        RecyclerView.ViewHolder(itemYapCardEmptyBinding.root) {
      fun onBind(position: Int,
                 paymentCard: Card?,
                 dimensions: IntArray,
                 onItemClickListener: OnItemClickListener?){

      }
    }

}