package co.yap.networking.customers.responsedtos.documents

import com.google.gson.annotations.SerializedName

data class CustomerDocuments (

	@SerializedName("creationDate") val creationDate : String,
	@SerializedName("createdBy") val createdBy : String,
	@SerializedName("updatedDate") val updatedDate : String,
	@SerializedName("updatedBy") val updatedBy : String,
	@SerializedName("customerUUID") val customerUUID : String,
	@SerializedName("documentType") val documentType : String,
	@SerializedName("contentType") val contentType : String,
	@SerializedName("fileName") val fileName : String,
	@SerializedName("customerId") val customerId : Int,
	@SerializedName("expired") val expired : Boolean,
	@SerializedName("uploadDate") val uploadDate : String,
	@SerializedName("pageNo") val pageNo : Int,
	@SerializedName("active") val active : Boolean,
	@SerializedName("documentInformation") val documentInformation : DocumentInformation
)