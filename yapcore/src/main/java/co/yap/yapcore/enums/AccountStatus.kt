package co.yap.yapcore.enums

import androidx.annotation.Keep

@Keep
enum class AccountStatus {
    ON_BOARDED, // notificationstatus become when kyc skiped and only signup done
    MEETING_SCHEDULED, // notificationstatus become when card ordered
    MEETING_SUCCESS, // notificationstatus become when meeting with FSS success
    CAPTURED_EID, // notificationstatus become when kyc done and address verification pending
    EID_EXPIRED,
    EID_UPDATED,
    EID_RESCAN_REQ,
    EID_FAILED,
    REJECTED,
    MEETING_FAILED, // notificationstatus become when meeting with FSS fail
    SOFT_KYC_DONE,
    SOFT_KYC_FAILED,
    PHYSICAL_CARD_ORDERED,
    CARD_ACTIVATED, // notificationstatus become when pin set
    PARNET_MOBILE_VERIFICATION_PENDING,
    PASS_CODE_PENDING,
    EMAIL_PENDING,
    INVITE_PENDING,
    INVITE_ACCEPTED,
    USER_VERIFIED,
    INVITE_DECLINED,
    BIRTH_INFO_COLLECTED,
    FATCA_GENERATED,
    CAPTURED_ADDRESS,
    INVITATION_PENDING,
    FSS_PROFILE_UPDATED;
}
