package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.widget.ImageViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListBinding
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator.getString
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils.FORMATE_TIME_24H
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.reformatStringDate
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.*


class TransactionsListingAdapter(private val list: MutableList<Content>) :
    BaseBindingRecyclerAdapter<Content, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder as TransactionListingViewHolder).onBind(list[position])
    }

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TransactionListingViewHolder(binding as ItemTransactionListBinding)
    }

    class TransactionListingViewHolder(private val itemTransactionListBinding: ItemTransactionListBinding) :
        RecyclerView.ViewHolder(itemTransactionListBinding.root) {

        fun onBind(transaction: Content) {
//            transaction.status = TransactionStatus.CANCELLED.name
            val context: Context = itemTransactionListBinding.tvCurrency.context
            handleProductBaseCases(context, transaction)

            transaction.transactionNote?.let {
                itemTransactionListBinding.tvTransactionNote.text = it
            }

            itemTransactionListBinding.tvTransactionNote.visibility =
                if (transaction.transactionNote.isNullOrEmpty() || transaction.transactionNote.equals(
                        "null"
                    )
                ) View.GONE else View.VISIBLE

            itemTransactionListBinding.tvCurrency.text = transaction.currency
            itemTransactionListBinding.ivIncoming.setImageResource(transaction.getTransactionTypeIcon())

            var txnAmountPreFix = ""
            transaction.txnType?.let {
                when (it) {
                    TxnType.CREDIT.type -> {
                        txnAmountPreFix = "+"
                        itemTransactionListBinding.tvTransactionAmount.setTextColor(
                            context.getColors(
                                R.color.colorSecondaryGreen
                            )
                        )
                    }
                    TxnType.DEBIT.type -> {
                        txnAmountPreFix = "-"
                        itemTransactionListBinding.tvTransactionAmount.setTextColor(
                            context.getColors(
                                R.color.colorPrimaryDark
                            )
                        )
                    }
                }
            }

            itemTransactionListBinding.tvTransactionAmount.text =
                String.format(
                    "%s %s", txnAmountPreFix,
                    if (TxnType.CREDIT.type == transaction.txnType) transaction.amount.toString().toFormattedCurrency() else transaction.totalAmount.toString().toFormattedCurrency()
                )
        }


        private fun handleProductBaseCases(context: Context, transaction: Content) {
            val transactionTitle = transaction.getTransactionTitle()
            val txnIconResId = transaction.getTransactionIcon()
            val categoryTitle: String =
                transaction.getTransactionTypeTitle()
            transaction.productCode?.let {
                if (transaction.status == TransactionStatus.CANCELLED.name) {
                    val bgiv = itemTransactionListBinding.ivTransaction.background
                    bgiv.setTint(context.getColors(R.color.greyNormalDark))
                    itemTransactionListBinding.ivTransaction.setImageResource(txnIconResId)
                } else {
                    if (TransactionProductCode.Y2Y_TRANSFER.pCode == it || TransactionProductCode.POS_PURCHASE.pCode == it) {
                        setY2YUserImage(transaction, itemTransactionListBinding)
                    }
                    if (txnIconResId != -1)
                        itemTransactionListBinding.ivTransaction.setImageResource(txnIconResId)
                    else
                        setInitialsAsTxnImage(transaction, itemTransactionListBinding)

                    ImageViewCompat.setImageTintList(
                        itemTransactionListBinding.ivTransaction,
                        ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                    )
                }
            }

            itemTransactionListBinding.tvTransactionName.text = transactionTitle
            itemTransactionListBinding.tvTransactionTimeAndCategory.text = getString(
                context,
                R.string.screen_fragment_home_transaction_time_category,
                reformatStringDate(
                    transaction.updatedDate ?: "", FORMAT_LONG_INPUT, FORMATE_TIME_24H
                ), categoryTitle
            )
        }

        private fun setY2YUserImage(
            transaction: Content,
            itemTransactionListBinding: ItemTransactionListBinding
        ) {
            ImageBinding.loadAvatar(
                itemTransactionListBinding.ivTransaction,
                if (TxnType.valueOf(
                        transaction.txnType ?: ""
                    ) == TxnType.DEBIT
                ) transaction.receiverProfilePictureUrl else transaction.senderProfilePictureUrl,
                if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName else transaction.senderName,
                android.R.color.transparent,
                R.dimen.text_size_h2
            )
        }


        private fun setInitialsAsTxnImage(
            transaction: Content,
            itemTransactionListBinding: ItemTransactionListBinding
        ) {
            ImageBinding.loadAvatar(
                itemTransactionListBinding.ivTransaction,
                "",
                transaction.title,
                android.R.color.transparent,
                R.dimen.text_size_h2
            )
        }
    }
}

