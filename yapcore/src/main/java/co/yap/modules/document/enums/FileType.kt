package co.yap.modules.document.enums

sealed class FileType {
    data class JPG(var jpg: String = "jpg") : FileType()
    data class PDF(var pdf: String = "pdf") : FileType()
    data class PNG(var png: String = "png") : FileType()
    data class JPEG(var jpeg: String = "jpeg") : FileType()
}