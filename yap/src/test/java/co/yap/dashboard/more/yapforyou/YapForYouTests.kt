package co.yap

import co.yap.base.BaseTestCase
import co.yap.modules.dashboard.more.yapforyou.YAPForYouAchievementsComposer
import co.yap.modules.dashboard.more.yapforyou.YAPForYouItemsComposer
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YAPForYouViewModel
import co.yap.networking.transactions.responsedtos.achievement.AchievementResponse
import co.yap.yapcore.enums.AchievementType
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

@RunWith(MockitoJUnitRunner::class)
class YapForYouTests : BaseTestCase() {

    lateinit var sut: YAPForYouViewModel

    @Before
    fun setUpVM() {
        sut = YAPForYouViewModel(context)
    }

    @Test
    fun test_after_onboarding_get_started_is_current_achievement() {
        val itemsComposer: YAPForYouItemsComposer = YAPForYouAchievementsComposer()
        val mockComposerItems =
            itemsComposer.compose(getMockApiResponse() as ArrayList<AchievementResponse>)
        val expectedValue =
            mockComposerItems.first { it.achievementType == AchievementType.GET_STARTED }
        val currentAchievement = sut.getCurrentAchievement(mockComposerItems)

        Assert.assertEquals(expectedValue, currentAchievement)
    }


    private fun getMockApiResponse(): List<AchievementResponse> {
        val gson = GsonBuilder().create();
        val itemType = object : TypeToken<List<AchievementResponse>>() {}.type

        return gson.fromJson<List<AchievementResponse>>(readJsonFile(), itemType)
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