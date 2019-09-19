package co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces

import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

interface IAddSpareCard {
    interface State : IBase.State {
        var cardType: String
        //add virtual card layout fields
        var virtualCardFee: String

        //add physical card layout fields

        var physicalCardFee: String
        var avaialableCardBalance: String
        var physicalCardAddressTitle: String
        var physicalCardAddressSubTitle: String
        var physicalCardAddressCheckVisibility: Int
        var physicalCardAddressButtonsVisibility: Int
        var toggleVisibility: Boolean
        var onChangeLocationClick: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var cardType: String
        val addSparePhysicalCardLogicHelper: AddSparePhysicalCardLogicHelper
        val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper
        var latitude: String
        var longitude: String
        var address: Address

        val ADD_VIRTUAL_SPARE_CLICK_EVENT: Int
        val ADD_PHYSICAL_SPARE_CLICK_EVENT: Int
        var isFromaddressScreen: Boolean
        var availableBalance: String
        var sharedPreferenceManager: SharedPreferenceManager

        //add virtual card layout
        fun handlePressOnAddVirtualCardSuccess(id: Int)

        fun handlePressOnConfirmVirtualCardPurchase(id: Int)

        //add physical card layout
        fun handlePressOnAddPhysicalCardSuccess(id: Int)

        fun handlePressOnConfirmPhysicalCardPurchase(id: Int)

        //add physical card Buttons layout
        fun handlePressOnConfirmLocation(id: Int)

        fun handlePressOnChangeLocation(id: Int)
        fun handlePressOnConfirmPhysicalCardLocation(id: Int)


        //api
        fun requestGetAccountBalanceRequest()

        fun requestGetCardFeeRequest()

        fun requestAddSpareVirtualCard()

        fun requestAddSparePhysicalCard(id: Int)
        fun requestGetAddressForPhysicalCard()
        fun updateAddressForPhysicalCard()
    }

    interface View : IBase.View<ViewModel> /*{
        var addSparePhysicalCardViewHelper: AddSparePhysicalCardViewHelper?
        var addSpareVirtualCardViewHelper: AddSpareVirtualCardViewHelper?

    }*/
}