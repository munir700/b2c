package co.yap.modules.dashboard.home.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.widget.ImageViewCompat.setImageTintList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListBinding
import co.yap.modules.others.helper.ImageBinding
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator.getString
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils.FORMATE_TIME_24H
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.reformatStringDate
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors


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

            val context: Context = itemTransactionListBinding.tvCurrency.context
            transaction.title = transaction.title ?: "Unknown"
            transaction.transactionNote?.let {
                itemTransactionListBinding.tvTransactionNote?.text = it
            }

            itemTransactionListBinding.tvTransactionNote?.visibility =
                if (transaction.transactionNote.isNullOrEmpty()) View.GONE else View.VISIBLE
            itemTransactionListBinding.tvCurrency?.text = transaction.currency


            transaction.txnType?.let {
                var txnAmountPreFix: String = ""
                when (TxnType.valueOf(it)) {
                    TxnType.CREDIT -> {
                        itemTransactionListBinding.ivIncoming?.setImageResource(R.drawable.ic_incoming_transaction)
                        txnAmountPreFix = "+"
                        itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                            context.getColors(
                                R.color.colorSecondaryGreen
                            )
                        )
                    }
                    TxnType.DEBIT -> {
                        itemTransactionListBinding.ivIncoming?.setImageResource(R.drawable.ic_outgoing_transaction)
                        txnAmountPreFix = "-"
                        itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                            context.getColors(
                                R.color.colorPrimaryDark
                            )
                        )
                    }
                }
                itemTransactionListBinding.tvTransactionAmount?.text =
                    String.format(
                        "%s %s", txnAmountPreFix,
                        Utils.getFormattedCurrency(transaction.totalAmount.toString())
                    )
            }
            handleProductBaseCases(context, transaction)
//            if (transaction.txnType?.toLowerCase() == "credit") {
//                itemTransactionListBinding.tvTransactionAmount?.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.colorSecondaryGreen
//                    )
//                )
//                itemTransactionListBinding.tvTransactionAmount?.text =
//                    String.format(
//                        "+ %s",
//                        Utils.getFormattedCurrency(transaction.amount.toString())
//                    )
//            } else if (transaction.txnType?.toLowerCase() == " debit ") {
//                itemTransactionListBinding.tvTransactionAmount?.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.colorPrimaryDark
//                    )
//                )
//                itemTransactionListBinding.tvTransactionAmount?.text =
//                    String.format(
//                        "- %s",
//                        Utils.getFormattedCurrency(transaction.amount.toString())
//                    )
//            }


//            itemTransactionListBinding.tvNameInitials?.text =
//                transaction.title?.let { Utils.shortName(it) }


//            if (transaction.productCode == Constants.Y_TO_Y_TRANSFER) {
//                itemTransactionListBinding.ivTransaction.setImageDrawable(context.getDrawable(R.drawable.ic_yap_to_yap))
//            } else {
//                if (transaction.productCode == Constants.TOP_UP_VIA_CARD) {
//                    itemTransactionListBinding.ivTransaction.setImageDrawable(context.getDrawable(R.drawable.ic_top_up))
//                } else {
//                    if (transaction.productCode == Constants.SUPP_WITHDRAW || transaction.txnType == Constants.SUPP_CARD_TOP_UP) {
//                        if (transaction.txnType == Constants.MANUAL_DEBIT) {
//                            itemTransactionListBinding.ivTransaction.setImageDrawable(
//                                context.getDrawable(
//                                    R.drawable.ic_minus_transactions
//                                )
//                            )
//                            itemTransactionListBinding.ivTransaction.setPadding(0)
//                        } else if (transaction.txnType == Constants.MANUAL_CREDIT) {
//                            itemTransactionListBinding.ivTransaction.setImageDrawable(
//                                context.getDrawable(
//                                    R.drawable.ic_plus_transactions
//                                )
//                            )
//                            itemTransactionListBinding.ivTransaction.setPadding(0)
//                        }
//                    } else if (transaction.txnType == Constants.MANUAL_DEBIT) {
//                        itemTransactionListBinding.ivTransaction.setImageDrawable(
//                            context.getDrawable(
//                                R.drawable.ic_outgoing
//                            )
//                        )
//                    } else if (transaction.txnType == Constants.MANUAL_CREDIT) {
//                        itemTransactionListBinding.ivTransaction.setImageDrawable(
//                            context.getDrawable(
//                                R.drawable.ic_incoming
//                            )
//                        )
//                    }
//                }
//
//            }

//            setDataAgainstProductCode(transaction.productCode!!, transaction)
            //itemTransactionListBinding.viewModel = YapStoreDetailItemViewModel(store)
            //itemTransactionListBinding.executePendingBindings()
        }

        private fun handleProductBaseCases(context: Context, transaction: Content) {
            var transactionTitle = transaction.title
            var categoryTitle: String = transaction.category ?: "TRANSACTION"

            transaction.productCode?.let {
                when (it) {
                    TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                        categoryTitle = "YAP to YAP transfer"
                        transactionTitle = String.format(
                            "%s %s",
                            if (transaction.txnType == TxnType.DEBIT.type) "To" else "From",
                            if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName
                                ?: transaction.title else transaction.senderName
                                ?: transaction.title
                        )
                        ImageBinding.loadAvatar(
                            itemTransactionListBinding.ivTransaction,
                            "",
                            if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName
                                ?: transaction.title ?: "" else transaction.senderName
                                ?: transaction.title ?: ""
                            , android.R.color.transparent
                        )
                    }
                    TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> {
                        categoryTitle = "Remove funds"
                        setImageTintList(
                            itemTransactionListBinding.ivTransaction,
                            ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                        );
                        itemTransactionListBinding.ivTransaction.setImageResource(R.drawable.ic_minus_transactions)
                    }
                    TransactionProductCode.CARD_REORDER.pCode -> {

                    }
                    TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode -> {
                        categoryTitle = "Add funds"
                        setImageTintList(
                            itemTransactionListBinding.ivTransaction,
                            ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                        );
                        itemTransactionListBinding.ivTransaction.setImageResource(R.drawable.ic_plus_transactions)

                    }
                    TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                        categoryTitle = "Top up by card"
                        setImageTintList(
                            itemTransactionListBinding.ivTransaction,
                            ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                        );
                        itemTransactionListBinding.ivTransaction.setImageResource(R.drawable.ic_top_up)
                    }

                    TransactionProductCode.SWIFT.pCode, TransactionProductCode.DOMESTIC.pCode,
                    TransactionProductCode.RMT.pCode, TransactionProductCode.UAEFTS.pCode -> {
                        categoryTitle = "Bank transfer"
                        setImageTintList(
                            itemTransactionListBinding.ivTransaction,
                            ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                        );
                        itemTransactionListBinding.ivTransaction?.setImageResource(R.drawable.ic_bank)
                    }

                    TransactionProductCode.CASH_PAYOUT.pCode -> {
                        categoryTitle = "Cash pick up"
                        itemTransactionListBinding.ivTransaction?.setImageResource(R.drawable.ic_cash)
                        setImageTintList(
                            itemTransactionListBinding.ivTransaction,
                            ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                        )
                    }
                    TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
                        itemTransactionListBinding.ivTransaction?.setImageResource(R.drawable.ic_incoming)

                    }
                    TransactionProductCode.FEE_DEDUCT.pCode -> {
                        itemTransactionListBinding.ivTransaction?.setImageResource(R.drawable.ic_outgoing)

                    }
                    TransactionProductCode.POS.pCode -> {

                    }

                    else -> {
                    }
                }
            }
            itemTransactionListBinding.tvTransactionName?.text = transactionTitle
            itemTransactionListBinding.tvTransactionTimeAndCategory?.text = getString(
                context,
                R.string.screen_fragment_home_transaction_time_category,
                reformatStringDate(
                    transaction.updatedDate ?: "", FORMAT_LONG_INPUT, FORMATE_TIME_24H
                ), categoryTitle
            )

        }

        @SuppressLint("SetTextI18n")
        private fun setDataAgainstProductCode(productCode: String, transaction: Content) {
            when (productCode) {
                Constants.Y_TO_Y_TRANSFER -> {
                    itemTransactionListBinding.tvTransactionName?.text =
                        "${StringUtils.getFirstname(transaction.senderName!!)} to ${StringUtils.getFirstname(
                            transaction.receiverName.toString()
                        )}"
                }
                else -> {
                    itemTransactionListBinding.tvTransactionName?.text = transaction.title
                }
            }
        }


//        private fun splitTimeString(timeString: String?): String {
//            val originalTimeStrings = timeString.split("T").toTypedArray()
//            var splitTimeStrings = originalTimeStrings[1].split(":").toTypedArray()
//            return splitTimeStrings[0] + ":" + splitTimeStrings[1]
//        }
    }
}