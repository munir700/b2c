package co.yap.networking.customers.responsedtos.documents

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("customerUUID") val customerUUID : String,
	@SerializedName("documentType") val documentType : String,
	@SerializedName("firstName") val firstName : String,
	@SerializedName("lastName") val lastName : String,
	@SerializedName("fullName") val fullName : String,
	@SerializedName("nationality") val nationality : String,
	@SerializedName("dateExpiry") val dateExpiry : String,
	@SerializedName("dateIssue") val dateIssue : String,
	@SerializedName("dob") val dob : String,
	@SerializedName("gender") val gender : String,
	@SerializedName("passportNumber") val passportNumber : String,
	@SerializedName("tradeName") val tradeName : String,
	@SerializedName("legalStatus") val legalStatus : String,
	@SerializedName("registrationAuthority") val registrationAuthority : String,
	@SerializedName("licenseNumber") val licenseNumber : String,
	@SerializedName("registrationNumber") val registrationNumber : String,
	@SerializedName("active") val active : Boolean,
	@SerializedName("customerDocuments") val customerDocuments : List<CustomerDocuments>
)