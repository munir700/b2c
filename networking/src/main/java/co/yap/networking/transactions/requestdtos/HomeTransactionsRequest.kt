package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

const val REQUEST_PAGE_SIZE = 250

data class HomeTransactionsRequest(
    @SerializedName("pageNo")
    var number: Int = 0,
    @SerializedName("pageSize")
    var size: Int = REQUEST_PAGE_SIZE,
    @SerializedName("amountStartRange")
    var amountStartRange: Double? = 0.0,
    @SerializedName("amountEndRange")
    var amountEndRange: Double? = 0.0,
    @SerializedName("txnType")
    var txnType: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("totalAppliedFilter")
    var totalAppliedFilter: Int = 0,
    @SerializedName("searchField")
    var searchField: String? = null,
    @SerializedName("merchantCategoryNames")
    var categories: ArrayList<String>? = null,
    @SerializedName("statuses")
    var statues: ArrayList<String>? = null,
    @SerializedName("cardDetailsRequired")
    val cardDetailsRequired: Boolean = true,
    @SerializedName("householdUUID")
    var householdUUID : String? = null,
    @SerializedName("txnCategories")
    var txnCategories: ArrayList<String>? = null,
)
