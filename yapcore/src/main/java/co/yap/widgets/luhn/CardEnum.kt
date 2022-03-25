package co.yap.widgets.luhn

import androidx.annotation.DrawableRes
import co.yap.yapcore.R

enum class CardEnum(val cardName: String, @DrawableRes val icon: Int,val  startWith: String, val length: String,val maxCvvLength:Int) {

    AmericanExpress("American Express", R.drawable.payment_ic_amex, "5392", "16",3),
    CardGuard("Card Guard", R.drawable.payment_ic_method, "5392", "16",3),
    ChinaUnionPay("China Union Pay", R.drawable.payment_ic_unionpay, "62", "16-19",3),
    Dankort("Dankort", R.drawable.payment_ic_method, "5019", "16",3),
    DinersClub("Diners Club", R.drawable.payment_ic_dinersclub, "300-305, 309, 36, 38, 39", "14,16-19",3),
    Discover("Discover", R.drawable.payment_ic_discover, "6011, 622126 to 622925, 644, 645, 646, 647, 648, 649, 65", "16,19",3),
    InstaPayment("Insta Payment", R.drawable.payment_ic_method, "637, 638, 639", "16",3),
    JCB("JCB", R.drawable.payment_ic_method, "3528-3589", "16-19",3),
    Maestro("Maestro", R.drawable.payment_ic_maestro_card, "5018, 5020, 5038, 5893, 6304, 6759, 6761, 6762, 6763", "12-19",3),
    MasterCard("Master", R.drawable.payment_ic_master_card, "51, 52, 53, 54, 55, 222100-272099", "16",3),
    MIR("Mir", R.drawable.payment_ic_method, "2200 - 2204", "16",3),
    Troy("Troy", R.drawable.payment_ic_method, "979200-979289", "16",3),
    UATP("Universal Air Travel Plan", R.drawable.payment_ic_method, "1", "15",3),
    VisaElectron("Visa Electron", R.drawable.payment_ic_method, "4026, 417500, 4508, 4844, 4913, 4917", "16",3),
    Visa("Visa", R.drawable.payment_ic_visa, "4", "13,16,19",3);

}