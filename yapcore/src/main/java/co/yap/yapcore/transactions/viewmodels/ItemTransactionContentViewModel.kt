package co.yap.yapcore.transactions.viewmodels

import co.yap.networking.transactions.responsedtos.transaction.Content

class ItemTransactionContentViewModel(
    val itemViewModel: Content?,
    val txnImageResId: Int? = null,
    val txnTxnStatusResId: Int = android.R.color.transparent
)