package co.yap.billpayments.billerdetail.composer

import android.text.InputType
import co.yap.billpayments.billerdetail.adapter.BillerDetailInputFieldModel
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogsModel


interface BillerDetailsInputComposer {
    fun compose(iotCatLogs: ArrayList<IoCatalogsModel>): MutableList<BillerDetailInputFieldModel>
}

class BillerDetailInputComposer : BillerDetailsInputComposer {
    override fun compose(iotCatLogs: ArrayList<IoCatalogsModel>): MutableList<BillerDetailInputFieldModel> {
        val list: MutableList<BillerDetailInputFieldModel> = mutableListOf()
        val inputTypes: MutableList<Pair<String, Int>> = mutableListOf()
        inputTypes.add(Pair("Alphanumeric", InputType.TYPE_CLASS_TEXT))
        inputTypes.add(Pair("Numeric", InputType.TYPE_CLASS_NUMBER))

        iotCatLogs.map { iotCatLog ->
            val inputType = inputTypes.find { it.equals(iotCatLog.dataType) }

            list.add(BillerDetailInputFieldModel(0,
                0,
                false,
                false,
                "",
                "",
                iotCatLog.description,
                "",
                iotCatLog.maxLength,
                iotCatLog.minLength,
                0,
                inputType?.second,
                iotCatLog.name,
                ""))
        }
        return list
    }
}
