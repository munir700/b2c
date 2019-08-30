package co.yap.modules.dashboard.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.TransactionAdapterArrayModel
import co.yap.modules.dashboard.models.TransactionAdapterModel
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.models.TransactionResponseDTO
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class YapHomeViewModel(application: Application) : BaseViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    private val maxTransactionVal: Int = 600
    private val cardsRepository: CardsRepository = CardsRepository

    override fun loadJSONDummyList(): ArrayList<TransactionAdapterModel> {
        var transactionsList = TransactionAdapterArrayModel(ArrayList())
        try {
            val jsonObject = JSONObject(loadTransactionFromJsonAssets(context))
            val jsonArray = jsonObject.getJSONArray("data")
            val arrayList: ArrayList<TransactionAdapterModel> = ArrayList<TransactionAdapterModel>()

            for (i in 0..(jsonArray.length() - 1)) {

                val jsonModel = jsonArray.getJSONObject(i)
                val transactionHeader = TransactionAdapterModel(
                    "HEADER",
                    jsonModel.getString("date"),
                    jsonModel.getString("totalAmount"),
                    jsonModel.getString("closingBalance"),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
                arrayList.add(transactionHeader)

                val jsonArrayChild = jsonModel.getJSONArray("transaction")

                for (i in 0..(jsonArrayChild.length() - 1)) {
                    val jsonTransaction = jsonArrayChild.getJSONObject(i)

                    val transactionItem = TransactionAdapterModel(
                        "ITEM",
                       "",
                        "",
                        "",
                        jsonTransaction.getString("vendor"),
                        jsonTransaction.getString("type"),
                        jsonTransaction.getString("imageUrl"),
                        jsonTransaction.getString("time"),
                        jsonTransaction.getString("category"),
                        jsonTransaction.getString("amount"),
                        jsonTransaction.getString("currency"),
                        jsonTransaction.getString("amountPercentage")
                    )
                    arrayList.add(transactionItem)

                }
            }
            transactionsList = TransactionAdapterArrayModel(arrayList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return transactionsList.data
    }

    private fun loadTransactionFromJsonAssets(context: Context): String? {
        val json: String?
        try {
            val `is` = context.assets.open("transactions.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getAmountPercentage(dataSet: Double): Double {

        val percentage = dataSet / maxTransactionVal * 100
        return Math.round(percentage ).toDouble()
    }

    override fun getGraphDummyData(): ArrayList<co.yap.modules.onboarding.models.TransactionModel> {

        var transactionsList: ArrayList<co.yap.modules.onboarding.models.TransactionModel> = ArrayList()

        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor five",
                1000.00,
                getAmountPercentage(1000.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            co.yap.modules.onboarding.models.TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )

   return transactionsList
    }

    override fun getDebitCards() {

        launch {
            state.loading = true
            when (val response = cardsRepository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size != 0) {
                        MyUserManager.cardSerialNumber = response.data.data[0].cardSerialNumber
                        clickEvent.setValue(EVENT_SET_CARD_PIN)
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

    }
}