package co.yap.yapcore.dagger.di.components

interface BaseComponent<T> {
    fun inject(target: T)
}