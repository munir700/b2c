package co.yap.yapcore.helpers

open class SingleSingletonHolder<out T : Any>(creator: () -> T) {
    private var creator: (() -> T)? = creator

    @Volatile
    private var instance: T? = null

    open fun get(): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!()
                instance = created
                creator = null
                created
            }
        }
    }
}