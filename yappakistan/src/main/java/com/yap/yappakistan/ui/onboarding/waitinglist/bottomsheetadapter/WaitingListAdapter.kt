package com.yap.yappakistan.ui.onboarding.waitinglist.bottomsheetadapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yap.core.base.BaseBindingRecyclerAdapter
import com.yap.core.base.interfaces.OnItemClickListener
import com.yap.uikit.widgets.nameinitialscircleimageview.NameInitialsCircleImageView
import com.yap.yappakistan.R
import com.yap.yappakistan.databinding.LayoutWaitingListHeaderBinding
import com.yap.yappakistan.databinding.LayoutWaitingListItemBinding
import com.yap.yappakistan.networking.microservices.customers.responsedtos.InviteeDetails

class WaitingListAdapter(
    private val list: MutableList<InviteeDetails>
) : BaseBindingRecyclerAdapter<InviteeDetails, WaitingListAdapter.WaitingListItemViewHolder>(
    list
) {
    override fun onCreateViewHolder(binding: ViewDataBinding): WaitingListItemViewHolder {
        return WaitingListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaitingListItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return when (viewType) {
            0 -> {
                R.layout.layout_waiting_list_header
            }
            1 -> {
                R.layout.layout_waiting_list_item
            }
            else -> R.layout.layout_waiting_list_item
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class WaitingListItemViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: InviteeDetails,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {
            when (binding) {
                is LayoutWaitingListHeaderBinding -> {
                    binding.viewModel =
                        WaitingListItemVM(
                            inviteeDetail = data,
                            position = position,
                            onItemClickListener = onItemClickListener
                        )
                }

                is LayoutWaitingListItemBinding -> {
                    binding.viewModel =
                        WaitingListItemVM(
                            inviteeDetail = data,
                            position = position,
                            onItemClickListener = onItemClickListener
                        )
                    binding.ivSignedUpUser.setImageInfo(
                        NameInitialsCircleImageView.ImageInfo
                        .Builder(data.inviteeCustomerName?:"")
                        .setIndex(position)
                        .build())
                    binding.clMain.setOnClickListener {

                    }
                }

            }
            binding.executePendingBindings()
        }
    }

}