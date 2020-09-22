package co.yap.yapcore.dagger.base.navigator

/**
 * @param <T>
</T> */
interface PlainConsumer<T> {
    /**
     * Consume the given value.
     * @param t the value
     */
    fun accept(t: T)
}
