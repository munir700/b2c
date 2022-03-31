package co.yap.modules.kyc.enums

enum class Gender(
    /**
     * The MRZ character.
     */
    val mrz: String
) {
    Male("M"), Female("F"), Unspecified("X");

    companion object {
        fun fromMrz(sex: String): Gender {
            return when (sex) {
                "M" -> Male
                "F" -> Female
                "<", "X" -> Unspecified
                else -> throw RuntimeException("Invalid MRZ sex character: $sex")
            }
        }
    }
}