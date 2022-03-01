package com.yap.yappakistan.ui.onboarding.waitinglist.bottomsheetadapter

import com.yap.core.base.interfaces.OnItemClickListener
import com.yap.yappakistan.networking.microservices.customers.responsedtos.InviteeDetails

class WaitingListItemVM(
    val inviteeDetail: InviteeDetails,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
)