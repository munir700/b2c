package co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces

import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddSpareCard {
    interface State : IBase.State {
        var cardType: String
        //add virtual card layout fields
        var virtualCardFee: String
        var virtualCardAvaialableBalance: String

        //add physical card layout fields
        var physicalCardFee: String
        var physicalCardAvaialableBalance: String
        var physicalCardAddressTitle: String
        var physicalCardAddressSubTitle: String
        var physicalCardAddressConfirmed: Boolean
        var physicalCardAddressCheckVisibility: Boolean
        var physicalCardAddressButtonsVisibility: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var cardType: String
        //add virtual card layout\
        fun handlePressOnAddVirtualCardSuccess(id: Int)

        fun handlePressOnConfirmVirtualCardPurchase(id: Int)

        //add physical card layout
        fun handlePressOnAddPhysicalCardSuccess(id: Int)

        fun handlePressOnConfirmPhysicalCardPurchase(id: Int)

        //add physical card Buttons layout
        fun handlePressOnConfirmLocation(id: Int)

        fun handlePressOnChangeLocation(id: Int)
        fun handlePressOnConfirmPhysicalCardLocation(id: Int)

        val addSparePhysicalCardLogicHelper: AddSparePhysicalCardLogicHelper
        val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper


    }

    interface View : IBase.View<ViewModel> /*{
        var addSparePhysicalCardViewHelper: AddSparePhysicalCardViewHelper?
        var addSpareVirtualCardViewHelper: AddSpareVirtualCardViewHelper?

    }*/
}