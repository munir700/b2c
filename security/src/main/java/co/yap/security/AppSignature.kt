package co.yap.security

data class AppSignature(var md5: String?, var sha1: String?, var sha256: String?) {

    override fun equals(other: Any?): Boolean {
        return if (other is AppSignature) {
            (this.sha1.equals(other.sha1, true)
                    && this.md5.equals(other.md5, true)
                    && this.sha256.equals(other.sha256, true))
        } else
            false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}