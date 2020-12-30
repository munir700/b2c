package co.yap.yapcore.enums

import co.yap.translation.Strings

enum class YFYAchievementTaskType(val type: String) {
    //Get started enums
    TOP_UP(Strings.common_add_money),
    SET_PROFILE_PICTURE("Set a profile picture"),
    OPEN_YAP_ACCOUNT("Open your YAP account"),
    ADD_CARD("Add a Card"),

    //Up & Running
    USE_YAP_LOCALLY("Use YAP locally"),
    FREEZE_AND_UNFREEZE("Freeze and unfreeze your card"),
    SPEND_HUNDRED_AED("Spend AED 100"),
    EXPLORE_CARD_CONTROLS("Explore card controls"),

    //Better Together
    INVITE_A_FRIEND("Invite a friend"),
    MAKE_Y2Y_TRANSFER("Make a Y2Y transfer"),
    SPLIT_BILLS("Split bills with YAP"),
    SEND_MONEY("Send money outside YAP"),

    //Take the leap
    ORDER_VIRTUAL_CARD("Order a virtual card"),
    UPGRADE_TO_PRIME("Upgrade to Prime"),
    GO_METAL("Go Metal"),
    SET_UP_MULTI_CURRENCY("Set up a multi-currency account"),

    //Yap Store
    GET_YOUNG("Get YAP Young"),
    SIGN_UP_TO_HOUSEHOLD("Sign up to Household"),
    MISSION_ON_YOUNG("Set missions on YAP Young"),
    PAY_YOU_HELP("Pay your help using Household"),

    // You are a pro
    INVITE_TEN_FRIENDS("Invite 10 friends to YAP"),
    SPEND_THOUSAND_AED("Spend AED 1000"),
    COMPLETE_RENEWAL("Complete a renewal"),

}
