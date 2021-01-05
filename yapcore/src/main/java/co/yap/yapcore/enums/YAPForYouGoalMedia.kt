package co.yap.yapcore.enums

sealed class YAPForYouGoalMedia {
    class LottieAnimation(val jsonFileName: String) : YAPForYouGoalMedia()
    class Image(val imageName: String) : YAPForYouGoalMedia()
}