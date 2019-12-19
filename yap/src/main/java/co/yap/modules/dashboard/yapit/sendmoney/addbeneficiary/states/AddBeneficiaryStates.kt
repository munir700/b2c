package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.yapcore.BaseState
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class AddBeneficiaryStates : BaseState(),
    IAddBeneficiary.State {

    var validateConfirmIban: Boolean = false

    @get:Bindable
    override var selectedBeneficiaryType: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedBeneficiaryType)
        }

    @get:Bindable
    override var countryCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryCode)
        }


    @get:Bindable
    override var flagDrawableResId: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.flagDrawableResId)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }


    @get:Bindable
    override var country: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.country)

        }


    @get:Bindable
    override var transferType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferType)

        }


    @get:Bindable
    override var currency: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)

        }

    @get:Bindable
    override var nickName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
            validate()
            validateCashFlowUser()
            validateDomesticUser()
        }

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            validate()
            validateCashFlowUser()
            validateDomesticUser()
        }

    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            validate()
            validateCashFlowUser()
            validateDomesticUser()
        }

    @get:Bindable
    override var drawbleRight: Drawable? = null
        set(value) {
            field = value
            notifyPropertyChanged(co.yap.BR.drawbleRight)

        }

    @get:Bindable
    override var mobileNo: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(co.yap.BR.mobile)
            if (mobileNo.length < 9) {
                mobileNoLength = 11
            }
        }

    @get:Bindable
    override var mobileNoLength: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(co.yap.BR.mobileNoLength)
        }

    fun validate() {
        if (!selectedBeneficiaryType.isNullOrEmpty()) {
            when (SendMoneyBeneficiaryType.valueOf(selectedBeneficiaryType!!)) {
                SendMoneyBeneficiaryType.RMT -> {
                    valid = nickName.length > 1 && firstName.length > 1 && lastName.length > 1
                }
                SendMoneyBeneficiaryType.SWIFT -> {

                }
                SendMoneyBeneficiaryType.DOMESTIC -> {

                }
                SendMoneyBeneficiaryType.CASHPAYOUT -> {

                }
                SendMoneyBeneficiaryType.UAEFTS -> {

                }
            }
        }
    }

    // fields for domestic user


    @get:Bindable
    override var validateDomesticButton: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.validateDomesticButton)
        }

    @get:Bindable
    override var country2DigitIsoCode: String = "AE"
        set(value) {
            field = value
            notifyPropertyChanged(BR.country2DigitIsoCode)
        }

    @get:Bindable
    override var validateCashflowButton: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.validateCashflowButton)
        }

    @get:Bindable
    override var iban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.iban)
            validateIban()
        }

    @get:Bindable
    override var confirmIban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmIban)
            validateIban()
        }

    //

    @get:Bindable
    override var id: Int = 11
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    override var beneficiaryId: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryId)
        }

    @get:Bindable
    override var accountUuid: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountUuid)
        }

    @get:Bindable
    override var beneficiaryType: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryType)
        }

    @get:Bindable
    override var title: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    override var accountNo: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountNo)
        }

    @get:Bindable
    override var lastUsedDate: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastUsedDate)
        }

    @get:Bindable
    override var swiftCode: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)
        }

    @get:Bindable
    override var bankName: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankName)
        }

    @get:Bindable
    override var branchName: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.branchName)
        }

    @get:Bindable
    override var branchAddress: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.branchAddress)
        }

    @get:Bindable
    override var identifierCode1: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.identifierCode1)
        }

    @get:Bindable
    override var identifierCode2: String? = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.identifierCode2)
        }

    fun validateIban() {
        validateDomesticButton =
            !iban.isNullOrEmpty() && !confirmIban.isNullOrEmpty() && iban == confirmIban
        validateConfirmIban = validateDomesticButton
        notifyPropertyChanged(BR.validateDomesticButton)
        validateDomesticUser()
//
//        if (!iban.isNullOrEmpty() && !confirmIban.isNullOrEmpty() && iban.equals(
//                confirmIban,
//                false
//            )
//        ) {
//            validateConfirmIban = true
//            validateDomesticUser()
//        }
    }

    fun validateCashFlowUser() {
        if (!lastName.isNullOrEmpty() && !firstName.isNullOrEmpty() && !nickName.isNullOrEmpty()) {
            validateCashflowButton = true
            notifyPropertyChanged(BR.validateCashflowButton)
        }
    }

    fun validateDomesticUser() {
        if (!lastName.isNullOrEmpty() && !firstName.isNullOrEmpty() && validateConfirmIban) {
            validateDomesticButton = true
            notifyPropertyChanged(BR.validateDomesticButton)
        } else {
            validateDomesticButton = false
            notifyPropertyChanged(BR.validateDomesticButton)
        }
    }

}