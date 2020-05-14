package co.yap.yapcore.transactions.viewmodels

import co.yap.networking.transactions.responsedtos.transaction.Transaction

class ItemTransactionContentViewModel(
    val itemViewModel: Transaction?,
    val txnImageResId: Int? = null,
    val txnTxnStatusResId: Int = android.R.color.transparent
)