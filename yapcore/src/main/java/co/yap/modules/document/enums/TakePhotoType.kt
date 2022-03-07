package co.yap.modules.document.enums

import co.yap.yapcore.R

sealed class TakePhotoType {
    data class TakePhoto(var tvTakePhoto: Int =  R.id.tvTakePhoto) : TakePhotoType()
    data class Browse(var tvbrowseFiles: Int = R.id.tvbrowseFiles) : TakePhotoType()
}