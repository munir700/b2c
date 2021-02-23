package co.yap.base

import co.yap.app.YAPApplication
import co.yap.networking.AppData
import co.yap.networking.RetroNetwork
import org.junit.After
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BaseTestCase {
    private var closeable: AutoCloseable? = null

    @Mock
    lateinit var context: YAPApplication

    @Mock
    lateinit var appData: AppData

    open fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        appData = AppData(baseUrl = "https://stg.yap.co")
        RetroNetwork.initWith(context, appData)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        closeable?.close()
    }
}