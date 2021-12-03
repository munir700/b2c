package co.yap.modules.dashboard.transaction.totalpurchases

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.R
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.TotalPurchaseRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.CoreCircularImageView
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.TransactionAdapterType
import co.yap.yapcore.helpers.extentions.getTitle
import co.yap.yapcore.helpers.extentions.loadImage
import co.yap.yapcore.helpers.extentions.setTransactionImage

class TotalPurchasesViewModel(application: Application) :
    BaseViewModel<ITotalPurchases.State>(application), ITotalPurchases.ViewModel {
    override val state: TotalPurchaseState = TotalPurchaseState()
    val repository: TransactionsRepository = TransactionsRepository
    override val adapter: TransactionsListingAdapter = TransactionsListingAdapter(
        arrayListOf(),
        TransactionAdapterType.TOTAL_PURCHASE
    )
    override var transaction: ObservableField<Transaction> = ObservableField()

    var list: List<Transaction> = arrayListOf()
    override fun onCreate() {
        super.onCreate()
        adapter.analyticType = Constants.TOTAL_PURCHASE
        getTotalPurchaseList()
    }

    fun transactionMerchantName(transaction: Transaction?): String? {
        return transaction?.let { txn ->
            txn.merchantName ?: txn.getTitle()
        }
    }

    override fun getTotalPurchaseList() {
        launch {
            state.viewState.postValue(true)
            when (val response = repository.getTotalPurchasesList(getTotalPurchaseRequest())) {
                is RetroApiResponse.Success -> {
                    list = response.data.data
                    adapter.setList(list)
                    state.viewState.value = false

                }
                is RetroApiResponse.Error -> {
                    showToast(response.error.message)
                    state.viewState.value = false
                }
            }
        }
    }

    override fun getTotalPurchaseRequest(): TotalPurchaseRequest {
        transaction.get()?.let { data ->
            return when (data.productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                    if (data.txnType == TxnType.DEBIT.type) TotalPurchaseRequest(
                        txnType = data.txnType
                            ?: "",
                        productCode = data.productCode ?: "",
                        receiverCustomerId = data.customerId2 ?: ""
                    )
                    else
                        TotalPurchaseRequest(
                            txnType = data.txnType ?: "",
                            productCode = data.productCode ?: "",
                            senderCustomerId = data.customerId2 ?: ""
                        )
                }
                TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode -> {
                    TotalPurchaseRequest(
                        txnType = data.txnType ?: "",
                        productCode = data.productCode ?: "",
                        beneficiaryId = data.beneficiaryId ?: ""
                    )
                }
                TransactionProductCode.ECOM.pCode, TransactionProductCode.POS_PURCHASE.pCode -> {
                    TotalPurchaseRequest(
                        txnType = data.txnType ?: "",
                        productCode = data.productCode ?: "", merchantName = data.merchantName
                    )
                }
                else -> TotalPurchaseRequest(
                    txnType = data.txnType ?: "",
                    productCode = data.productCode ?: ""
                )
            }

        }
        return TotalPurchaseRequest(
            txnType = transaction.get()?.txnType ?: "",
            productCode = transaction.get()?.productCode ?: ""
        )
    }

    override fun setMerchantImage(coreCircularImageView: CoreCircularImageView) {
        transaction.get()?.let { txns ->
            if (txns.productCode != TransactionProductCode.ATM_DEPOSIT.pCode && txns.productCode != TransactionProductCode.ATM_WITHDRAWL.pCode) {
                txns.merchantLogo?.let { logo ->
                    coreCircularImageView.loadImage(logo)
                    if (txns.productCode == TransactionProductCode.ECOM.pCode || txns.productCode == TransactionProductCode.POS_PURCHASE.pCode)
                        coreCircularImageView.setBackgroundColor(context.getColor(R.color.white))
                } ?: txns.setTransactionImage(coreCircularImageView)
            } else {
                txns.setTransactionImage(coreCircularImageView)
            }
        }
    }
}