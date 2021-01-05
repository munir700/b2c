package co.yap.yapcore.enums

sealed class YAPForYouGoalMedia {
    class lottieAnimation(val jsonFileName: String) : YAPForYouGoalMedia()
    class image(val imageName: String) : YAPForYouGoalMedia()
}