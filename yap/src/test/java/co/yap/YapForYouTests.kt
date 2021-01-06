package co.yap

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.more.yapforyou.Y4YGraphComposer
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.networking.transactions.responsedtos.achievement.AchievementTask
import org.json.JSONObject
import org.junit.Test
import java.io.IOException
import java.nio.charset.StandardCharsets

class YapForYouTests {

//    @Test
//    fun test_after_onboarding_get_started_is_current_achievement() {
//        val y4yComposer: IY4YComposer = Y4YGraphComposer()
//        val mockData = getMockApiResponse()
//        val mockComposerItems = y4yComposer.compose(mockData as ArrayList<Achievement>)
//        val a = getCurrentAchievement(mockComposerItems)
//        print(a.toString())
//
//    }
//
//    fun getCurrentAchievement(from: ArrayList<Y4YAchievementData>): Y4YAchievementData {
//        val sortedList = from.sortedWith(compareBy({ it.completedPercentage }, { it.lastUpdated }))
//
//        return sortedList.first()
//    }
//
//
//    fun getMockApiResponse(): List<Achievement> {
//        val list: ArrayList<Achievement> = arrayListOf()
//        val mainObj =
//            JSONObject(loadTransactionFromJsonAssets(Application().applicationContext) ?: "")
//        val mainDataList = mainObj.getJSONArray("data")
//        for (i in 0 until mainDataList.length()) {
//            val tasksList: ArrayList<AchievementTask> = arrayListOf()
//            val parentArrayList = mainDataList.getJSONObject(i)
//            val title: String = parentArrayList.getString("title")
//            val color: String = parentArrayList.getString("colorCode")
//            val percentage: Double = parentArrayList.getDouble("percentage")
//            val acheivementType: String = parentArrayList.getString("achievementType")
//            val order: Int = parentArrayList.getInt("order")
//            val lock = parentArrayList.getBoolean("lock")
//            val lastUpdated = parentArrayList.getString("lastUpdated")
//            val tasks = parentArrayList.getJSONArray("tasks")
//            for (j in 0 until tasks.length()) {
//                tasksList.add(
//                    AchievementTask(
//                        title = tasks.getJSONObject(j).getString("title"),
//                        completion = tasks.getJSONObject(j).getBoolean("completion"),
//                        achievementTaskType = tasks.getJSONObject(j).getString("taskType")
//                    )
//                )
//            }
//            list.add(
//                Achievement(
//                    title = title,
//                    color = color,
//                    percentage = 0.0,
//                    achievementType = acheivementType,
//                    order = order,
//                    isForceLocked = false,
//                    tasks = arrayListOf(),
//                    lastUpdated = lastUpdated
//                )
//            )
//        }
//        return list
//    }
//
//    private fun loadTransactionFromJsonAssets(context: Context): String? {
//        val json: String?
//        try {
//            val `is` = context.assets.open("yapachievement.json")
//            val size = `is`.available()
//            val buffer = ByteArray(size)
//            `is`.read(buffer)
//            `is`.close()
//            json = String(buffer, StandardCharsets.UTF_8)
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return null
//        }
//        return json
//    }

}