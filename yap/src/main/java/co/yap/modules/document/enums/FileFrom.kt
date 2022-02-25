package co.yap.modules.document.enums

sealed class FileFrom {
    data class Local(var local: String = "local") : FileFrom()
    data class Link(var link: String = "link") : FileFrom()
}