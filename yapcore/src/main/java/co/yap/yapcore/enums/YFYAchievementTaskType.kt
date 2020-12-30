package co.yap.yapcore.enums

import co.yap.translation.Strings

enum class YFYAchievementTaskType(val title: String) {
    //Get started enums
    OPEN_YOUR_YAP_ACCOUNT("Open your YAP account"),
    SET_PIN("Set your PIN"),
    TOP_UP(Strings.common_add_money),
    SET_PROFILE_PICTURE("Set a profile picture"),

    //Up & Running
    USE_YAP_LOCALLY("Use YAP locally"),
    FREEZE_UNFREEZE_CARD("Freeze and unfreeze your card"),
    SPEND_AMOUNT("Spend AED 100"),
    EXPLORE_CARD_CONTROLS("Explore card controls"),

    //Better Together
    INVITE_FRIEND("Invite a friend"),
    MAKE_Y2Y_TRANSFER("Make a Y2Y transfer"),
    SPLIT_BILLS_WITH_YAP("Split bills with YAP"),
    SEND_MONEY_OUTSIDE_YAP("Send money outside YAP"),

    //Take the leap
    ORDER_SPARE_CARD("Order a virtual card"),
    UPGRADE_TO_PRIME("Upgrade to Prime"),
    UPGRADE_TO_METAL("Go Metal"),
    SETUP_MULTI_CURRENCY("Set up a multi-currency account"),

    //Yap Store
    GET_YAP_YOUNG("Get YAP Young"),
    GET_YAP_HOUSEHOLD("Sign up to Household"),
    SET_MISSIONS_ON_YAP_YOUNG("Set missions on YAP Young"),
    SALARY_TRANSFER_ON_YAP_HOUSEHOLD("Pay your help using Household"),

    // You are a pro
    INVITE_TEN_FRIENDS("Invite 10 friends to YAP"),
    SPEND_THOUSAND_AED("Spend AED 1000"),
    COMPLETE_RENEWAL("Complete a renewal"),

}
