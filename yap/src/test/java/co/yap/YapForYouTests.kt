package co.yap

import co.yap.app.YAPApplication
import co.yap.base.BaseTestCase
import co.yap.modules.dashboard.more.yapforyou.Y4YGraphComposer
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YAPForYouViewModel
import co.yap.networking.AppData
import co.yap.networking.RetroNetwork
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.enums.AchievementType
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

@RunWith(MockitoJUnitRunner::class)
class YapForYouTests : BaseTestCase() {

    @Mock
    lateinit var context: YAPApplication

    @Mock
    lateinit var appData: AppData

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        appData = AppData(baseUrl = "https://stg.yap.co")
        RetroNetwork.initWith(context, appData)
    }

    @Test
    fun test_after_onboarding_get_started_is_current_achievement() {
        val y4yComposer: IY4YComposer = Y4YGraphComposer()
        val mockComposerItems = y4yComposer.compose(getMockApiResponse() as ArrayList<Achievement>)
        val expectedValue =
            mockComposerItems.first { it.achievementType == AchievementType.GET_STARTED }
        val sut = YAPForYouViewModel(context)
        val currentAchievement = sut.getCurrentAchievement(mockComposerItems)
        Assert.assertEquals(expectedValue, currentAchievement)
    }

    private fun getMockApiResponse(): List<Achievement> {
        val gson = GsonBuilder().create();
        val itemType = object : TypeToken<List<Achievement>>() {}.type

        return gson.fromJson<List<Achievement>>(readJsonFile(), itemType)
    }

    @Throws(IOException::class)
    private fun readJsonFile(): String? {
        val br =
            BufferedReader(InputStreamReader(FileInputStream("../yap/src/main/assets/y4yMockResponse.json")))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }
}