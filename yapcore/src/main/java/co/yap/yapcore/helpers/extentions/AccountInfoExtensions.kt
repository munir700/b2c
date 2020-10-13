package co.yap.yapcore.helpers.extentions

import android.content.Context
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.R
import co.yap.yapcore.enums.AccountBlockSeverityLevel
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.enums.UserAccessRestriction
import co.yap.yapcore.helpers.Utils

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

fun AccountInfo?.getBlockedFeaturesList(key: UserAccessRestriction): ArrayList<FeatureSet> {
    return (when (key) {
        UserAccessRestriction.CARD_FREEZE_BY_APP, UserAccessRestriction.CARD_FREEZE_BY_CSR -> {
            arrayListOf()
        }
        UserAccessRestriction.CARD_HOTLISTED_BY_APP, UserAccessRestriction.CARD_HOTLISTED_BY_CSR, UserAccessRestriction.CARD_BLOCKED_BY_MASTER_CARD -> {
            arrayListOf(
                FeatureSet.UNFREEZE_CARD,
                FeatureSet.CHANGE_PIN,
                FeatureSet.FORGOT_PIN
            )
        }

        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_TOTAL -> {
            arrayListOf(
                FeatureSet.DOMESTIC_TRANSFER,
                FeatureSet.UAEFTS_TRANSFER,
                FeatureSet.RMT_TRANSFER,
                FeatureSet.SWIFT_TRANSFER,
                FeatureSet.CBWSI_TRANSFER,
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD,
                FeatureSet.Y2Y_TRANSFER,
                FeatureSet.UNFREEZE_CARD
            )
        }
        UserAccessRestriction.IBAN_BLOCKED_BY_RAK_DEBIT -> {
            arrayListOf(
                FeatureSet.DOMESTIC_TRANSFER,
                FeatureSet.UAEFTS_TRANSFER,
                FeatureSet.RMT_TRANSFER,
                FeatureSet.SWIFT_TRANSFER,
                FeatureSet.CBWSI_TRANSFER,
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.Y2Y_TRANSFER,
                FeatureSet.UNFREEZE_CARD
            )
        }
        UserAccessRestriction.IBAN_BLCOKED_BY_RAK_CREDIT -> {
            arrayListOf(
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD,
                FeatureSet.UNFREEZE_CARD
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_TOTAL -> {
            arrayListOf(
                FeatureSet.DOMESTIC_TRANSFER,
                FeatureSet.UAEFTS_TRANSFER,
                FeatureSet.RMT_TRANSFER,
                FeatureSet.SWIFT_TRANSFER,
                FeatureSet.CBWSI_TRANSFER,
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD,
                FeatureSet.Y2Y_TRANSFER,
                FeatureSet.UNFREEZE_CARD,
                FeatureSet.CHANGE_PIN,
                FeatureSet.FORGOT_PIN
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_DEBIT -> {
            arrayListOf(
                FeatureSet.DOMESTIC_TRANSFER,
                FeatureSet.UAEFTS_TRANSFER,
                FeatureSet.RMT_TRANSFER,
                FeatureSet.SWIFT_TRANSFER,
                FeatureSet.CBWSI_TRANSFER,
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.Y2Y_TRANSFER,
                FeatureSet.UNFREEZE_CARD,
                FeatureSet.CHANGE_PIN,
                FeatureSet.FORGOT_PIN
            )
        }
        UserAccessRestriction.CARD_BLOCKED_BY_YAP_CREDIT -> {
            arrayListOf(
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.UNFREEZE_CARD,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD,
                FeatureSet.CHANGE_PIN,
                FeatureSet.FORGOT_PIN
            )
        }
        UserAccessRestriction.OTP_BLOCKED -> {
            arrayListOf(
                FeatureSet.DOMESTIC_TRANSFER,
                FeatureSet.UAEFTS_TRANSFER,
                FeatureSet.RMT_TRANSFER,
                FeatureSet.SWIFT_TRANSFER,
                FeatureSet.CBWSI_TRANSFER,
                FeatureSet.ADD_FUNDS,
                FeatureSet.REMOVE_FUNDS,
                FeatureSet.TOP_UP_BY_EXTERNAL_CARD,
                FeatureSet.Y2Y_TRANSFER,
                FeatureSet.UNFREEZE_CARD,
                FeatureSet.CHANGE_PIN,
                FeatureSet.FORGOT_PIN,
                FeatureSet.CHANGE_PASSCODE,
                FeatureSet.FORGOT_PASSCODE,
                FeatureSet.ADD_SEND_MONEY_BENEFICIARY,
                FeatureSet.EDIT_SEND_MONEY_BENEFICIARY
            )
        }
        UserAccessRestriction.NONE, UserAccessRestriction.ACCOUNT_INACTIVE, UserAccessRestriction.EID_EXPIRED -> {
            arrayListOf()
        }
    })
}

fun AccountInfo.getBlockedMessage(key: UserAccessRestriction, context: Context): String {
    return (when (key) {
        UserAccessRestriction.ACCOUNT_INACTIVE -> {
            context.resources.getString(R.string.screen_popup_activation_pending_display_text_message)
        }
        UserAccessRestriction.EID_EXPIRED -> {
            "EID Expired"
        }
        UserAccessRestriction.OTP_BLOCKED -> {
            Utils.getOtpBlockedMessage(context)
        }
        UserAccessRestriction.NONE -> {
            "None"
        }
        else -> {
            "Some of your card's features are temporarily disabled. Get in touch with us at +971 600551214 for assistance."
        }
    }
            )
}