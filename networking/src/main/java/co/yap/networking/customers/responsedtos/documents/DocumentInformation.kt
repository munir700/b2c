package co.yap.networking.customers.responsedtos.documents

import com.google.gson.annotations.SerializedName

data class DocumentInformation (

	@SerializedName("creationDate") val creationDate : String,
	@SerializedName("createdBy") val createdBy : String,
	@SerializedName("updatedDate") val updatedDate : String,
	@SerializedName("updatedBy") val updatedBy : String,
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
	@SerializedName("active") val active : Boolean,
	@SerializedName("identityNo") val identityNo : Int
)