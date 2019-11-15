package co.yap.networking.customers.responsedtos.sendmoney

import java.io.Serializable


open class Beneficiary : Serializable {

    var id: Int = 0
    var beneficiaryId: String? = null
    var accountUuid: String? = null
    var beneficiaryType: String? = null
    var mobileNo: String? = null
    var title: String? = null
    var accountNo: String? = null
    var lastUsedDate: String? = null
    var currency: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var swiftCode: String? = null
    var country: String? = null
    var bankName: String? = null
    var branchName: String? = null
    var branchAddress: String? = null
    var identifierCode1: String? = null
    var identifierCode2: String? = null


    var beneficiaryCountry: Any? = null
//    var beneficiaryCountry: Country? = null


//        if (beneficiaryCountry == null) {
//            beneficiaryCountry = Country()
//            beneficiaryCountry!!.isoCountryCode2Digit(country)
//            val flagResName = "flag_" + country!!.toLowerCase()
//            val flag = CurrencyUtils.getFlagDrawable(context, flagResName)
//            beneficiaryCountry!!.setFlagDrawableResId(flag)
//        }
//        return beneficiaryCountry
//    }

    override fun toString(): String {
        return "Beneficiary{" +
                "id=" + id +
                ", beneficiaryId='" + beneficiaryId + '\''.toString() +
                ", accountUuid='" + accountUuid + '\''.toString() +
                ", beneficiaryType='" + beneficiaryType + '\''.toString() +
                ", mobileNo='" + mobileNo + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", accountNo='" + accountNo + '\''.toString() +
                ", lastUsedDate='" + lastUsedDate + '\''.toString() +
                ", currency='" + currency + '\''.toString() +
                ", firstName='" + firstName + '\''.toString() +
                ", lastName='" + lastName + '\''.toString() +
                ", swiftCode='" + swiftCode + '\''.toString() +
                ", country='" + country + '\''.toString() +
                ", bankName='" + bankName + '\''.toString() +
                ", branchName='" + branchName + '\''.toString() +
                ", branchAddress='" + branchAddress + '\''.toString() +
                ", identifierCode1='" + identifierCode1 + '\''.toString() +
                ", identifierCode2='" + identifierCode2 + '\''.toString() +
                ", beneficiaryCountry=" + beneficiaryCountry +
                '}'.toString()
    }
}
