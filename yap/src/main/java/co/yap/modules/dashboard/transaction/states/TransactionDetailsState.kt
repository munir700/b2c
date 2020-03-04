package co.yap.modules.dashboard.transaction.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.yapcore.BaseState

class TransactionDetailsState : BaseState(), ITransactionDetails.State {

    @get:Bindable
    override var toolBarTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
        }

    @get:Bindable
    override var transactionTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transactionTitle)
        }

    @get:Bindable
    override var spentTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.spentTitle)
        }
    @get:Bindable
    override var spentAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.spentAmount)
        }

    @get:Bindable
    override var feeTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeTitle)
        }

    @get:Bindable
    override var feeAmount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.feeAmount)
        }

    @get:Bindable
    override var totalTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalTitle)
        }

    @get:Bindable
    override var totalAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalAmount)
        }

    @get:Bindable
    override var addNoteTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.addNoteTitle)
        }

    @get:Bindable
    override var noteValue: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.noteValue)
        }
    @get:Bindable
    override var currency: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)
        }
    @get:Bindable
    override var vatAmount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.vatAmount)
        }
    @get:Bindable
    override var totalAmountCalculated: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalAmountCalculated)
        }
//    @get:Bindable
//    override var content: Content? = null
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.content)
//        }

    override var isYtoYTransfer: ObservableField<Boolean> = ObservableField(false)


    override var spentVisibility: ObservableField<Boolean> = ObservableField(false)
        set(value) {
            field = ObservableField(value)
        }

    @get:Bindable
    override var transactionSender: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transactionSender)
        }

    @get:Bindable
    override var transactionReceiver: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transactionReceiver)
        }

    override var categoryTitle: ObservableField<String> = ObservableField("")
    override var categoryIcon: ObservableField<Int> = ObservableField()
    override var categoryName: ObservableField<String> = ObservableField()
    override var transactionAddress: ObservableField<String> = ObservableField("")
}