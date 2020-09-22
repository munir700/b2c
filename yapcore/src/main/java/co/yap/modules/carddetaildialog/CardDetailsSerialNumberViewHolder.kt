package co.yap.modules.carddetaildialog

import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.databinding.DialogCardDetailsCardSerialNumberBinding

class CardDetailsSerialNumberViewHolder(private val dialogCardDetailsCardSerialNumberBinding: DialogCardDetailsCardSerialNumberBinding) :
    RecyclerView.ViewHolder(dialogCardDetailsCardSerialNumberBinding.root) {
    fun onBind(
        cardDetailsModel: CardDetailsModel,
        position: Int
    ) {
        /*val params = dialogCardDetailsCardSerialNumberBinding.ivCard.layoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        dialogCardDetailsCardSerialNumberBinding.ivCard.layoutParams = params*/

        //dialogCardDetailsCardSerialNumberBinding.ivCard.loadImage(houseHoldCardsDesignModel.frontSideDesignImage?:"")
        dialogCardDetailsCardSerialNumberBinding.tvCardType.text = cardDetailsModel.cardType
        dialogCardDetailsCardSerialNumberBinding.tvCardNumberValue.text =
            cardDetailsModel.cardNumber


        dialogCardDetailsCardSerialNumberBinding.viewModel?.position = position
        dialogCardDetailsCardSerialNumberBinding.viewModel =
            CardDetailsDialogItemViewModel(
                position,
                cardDetailsModel
            )
        dialogCardDetailsCardSerialNumberBinding.executePendingBindings()
    }
}