package co.yap.networking.transactions.responsedtos.categorybar

import com.google.gson.annotations.SerializedName

data class Categories(
	@SerializedName("title") val title: String? = "",
	@SerializedName("txnCount") val txnCount: Int? = 0,
	@SerializedName("totalSpending") val totalSpending: Double? = 0.0,
	@SerializedName("totalSpendingInPercentage") val totalSpendingInPercentage: Double? = 0.0,
	@SerializedName("logoUrl") val logoUrl: String,
	@SerializedName("yapCategoryId") val yapCategoryId: Int? = 0,
	@SerializedName("date") val date: String? = "",
	@SerializedName("categoryWisePercentage") var categoryWisePercentage: Float,
	@SerializedName("noOfCategories") val noOfCategories: Int? = 0
)