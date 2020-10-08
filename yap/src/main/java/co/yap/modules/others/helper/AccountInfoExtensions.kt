package co.yap.modules.others.helper

import co.yap.modules.others.enums.FeatureSet
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.enums.AccountBlockSeverityLevel
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.enums.UserAccessRestriction

fun AccountInfo.getUserAccessRestrictions(): ArrayList<UserAccessRestriction> {
    val restrictions: ArrayList<UserAccessRestriction> = arrayListOf()

    if (partnerBankStatus?.equals(PartnerBankStatus.ACTIVATED.status) == true) {
        restrictions.add(UserAccessRestriction.ACCOUNT_INACTIVE)
    }
    if (otpBlocked == true) {
        restrictions.add(UserAccessRestriction.OTP_BLOCKED)
    }

    restrictions.add(
        when (this.freezeInitiator) {
            "MOBILE_APP_HOSTLIST" -> {
                UserAccessRestriction.CARD_HOTLISTED_BY_APP
            }
            "CUSTOMER_REQUEST_HOSTLISTED" -> {
                UserAccessRestriction.CARD_HOTLISTED_BY_CSR
            }
            "BANK_REQUEST" -> {
                when (this.severityLevel) {
                    AccountBlockSeverityLevel.TOTAL_BLOCK.freezeCode -> {
                        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_TOTAL
                    }
                    AccountBlockSeverityLevel.DEBIT_BLOCK.freezeCode -> {
                        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_DEBIT
                    }
                    AccountBlockSeverityLevel.CREDIT_BLOCK.freezeCode -> {
                        UserAccessRestriction.IBAN_BLCOKED_BY_RAK_CREDIT
                    }
                    else -> UserAccessRestriction.NONE
                }
            }
            "MASTER_CARD_REQUEST" -> {
                UserAccessRestriction.CARD_BLOCKED_BY_MASTER_CARD
            }
            "YAP_COMPLIANCE_TOTAL" -> {
                UserAccessRestriction.CARD_BLOCKED_BY_YAP_TOTAL
            }
            "YAP_COMPLIANCE_DEBIT" -> {
                UserAccessRestriction.CARD_BLOCKED_BY_YAP_DEBIT
            }
            "YAP_COMPLIANCE_CREDIT" -> {
                UserAccessRestriction.CARD_BLOCKED_BY_YAP_CREDIT
            }
            else -> UserAccessRestriction.NONE
        }
    )

    return restrictions
}

fun AccountInfo?.getBlockedFeaturesList(key: UserAccessRestriction): ArrayList<String> {
    return (when (key) {
        UserAccessRestriction.CARD_FREEZE_BY_APP, UserAccessRestriction.CARD_FREEZE_BY_CSR -> {
            arrayListOf()
        }
        UserAccessRestriction.CARD_HOTLISTED_BY_APP, UserAccessRestriction.CARD_HOTLISTED_BY_CSR, UserAccessRestriction.CARD_BLOCKED_BY_MASTER_CARD -> {
            arrayListOf(
                FeatureSet.UNFREEZE_CARD.screenName,
                FeatureSet.CHANGE_PIN.screenName,
                FeatureSet.FORGOT_PIN.screenName
            )
        }

        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_TOTAL -> {
            arrayListOf(
                FeatureSet.DOMESTIC.screenName,
                FeatureSet.UAEFTS.screenName,
                FeatureSet.RMT.screenName,
                FeatureSet.SWIFT.screenName,
                FeatureSet.CBWSI.screenName,
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD.screenName,
                FeatureSet.Y2Y_TRANSFER.screenName,
                FeatureSet.UNFREEZE_CARD.screenName
            )
        }
        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_DEBIT -> {
            arrayListOf(
                FeatureSet.DOMESTIC.screenName,
                FeatureSet.UAEFTS.screenName,
                FeatureSet.RMT.screenName,
                FeatureSet.SWIFT.screenName,
                FeatureSet.CBWSI.screenName,
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.Y2Y_TRANSFER.screenName,
                FeatureSet.UNFREEZE_CARD.screenName
            )
        }
        UserAccessRestriction.IBAN_BLCOKED_BY_RAK_CREDIT -> {
            arrayListOf(
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD.screenName,
                FeatureSet.UNFREEZE_CARD.screenName
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_TOTAL -> {
            arrayListOf(
                FeatureSet.DOMESTIC.screenName,
                FeatureSet.UAEFTS.screenName,
                FeatureSet.RMT.screenName,
                FeatureSet.SWIFT.screenName,
                FeatureSet.CBWSI.screenName,
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD.screenName,
                FeatureSet.Y2Y_TRANSFER.screenName,
                FeatureSet.UNFREEZE_CARD.screenName,
                FeatureSet.CHANGE_PIN.screenName,
                FeatureSet.FORGOT_PIN.screenName
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_DEBIT -> {
            arrayListOf(
                FeatureSet.DOMESTIC.screenName,
                FeatureSet.UAEFTS.screenName,
                FeatureSet.RMT.screenName,
                FeatureSet.SWIFT.screenName,
                FeatureSet.CBWSI.screenName,
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.Y2Y_TRANSFER.screenName,
                FeatureSet.UNFREEZE_CARD.screenName,
                FeatureSet.CHANGE_PIN.screenName,
                FeatureSet.FORGOT_PIN.screenName
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_CREDIT -> {
            arrayListOf(
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.UNFREEZE_CARD.screenName,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD.screenName,
                FeatureSet.CHANGE_PIN.screenName,
                FeatureSet.FORGOT_PIN.screenName
            )
        }
        UserAccessRestriction.OTP_BLOCKED -> {
            arrayListOf(
                FeatureSet.DOMESTIC.screenName,
                FeatureSet.UAEFTS.screenName,
                FeatureSet.RMT.screenName,
                FeatureSet.SWIFT.screenName,
                FeatureSet.CBWSI.screenName,
                FeatureSet.ADD_FUNDS.screenName,
                FeatureSet.REMOVE_FUNDS.screenName,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD.screenName,
                FeatureSet.Y2Y_TRANSFER.screenName,
                FeatureSet.UNFREEZE_CARD.screenName,
                FeatureSet.CHANGE_PIN.screenName,
                FeatureSet.FORGOT_PIN.screenName
            )
        }
        UserAccessRestriction.NONE, UserAccessRestriction.ACCOUNT_INACTIVE, UserAccessRestriction.EID_EXPIRED -> {
            arrayListOf()
        }
    })
}