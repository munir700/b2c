package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.more.yapforyou.states.YapForYouMainState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.networking.transactions.responsedtos.achievement.AchievementTask
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class YapForYouMainViewModel(application: Application) :
    BaseViewModel<IYapForYouMain.State>(application),
    IYapForYouMain.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override val repository: TransactionsRepository = TransactionsRepository

    override val state: YapForYouMainState = YapForYouMainState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedAchievement: ObservableField<Y4YAchievementData?> = ObservableField()
    override var selectedAchievementGoal: ObservableField<YAPForYouGoal?> = ObservableField()
    override var achievementsList: MutableList<Y4YAchievementData> = mutableListOf()
    override var achievementsResponse: MutableLiveData<ArrayList<Achievement>> =
        MutableLiveData(arrayListOf())

    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getAchievements() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getAchievements()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        achievementsResponse.value = (response.data.data as ArrayList<Achievement>)
                    }

                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun getMockApiResponse() {
        launch {
            val list: ArrayList<Achievement> = arrayListOf()
            state.loading = true
            delay(500)
            val mainObj = JSONObject(loadTransactionFromJsonAssets(context) ?: "")
            val mainDataList = mainObj.getJSONArray("data")
            for (i in 0 until mainDataList.length()) {
                val tasksList: ArrayList<AchievementTask> = arrayListOf()
                val parentArrayList = mainDataList.getJSONObject(i)
                val title: String = parentArrayList.getString("title")
                val color: String = parentArrayList.getString("colorCode")
                val percentage: Double = parentArrayList.getDouble("percentage")
                val acheivementType: String = parentArrayList.getString("achievementType")
                val order: Int = parentArrayList.getInt("order")
                val lock = parentArrayList.getBoolean("lock")
                val tasks = parentArrayList.getJSONArray("tasks")
                for (j in 0 until tasks.length()) {
                    tasksList.add(
                        AchievementTask(
                            title = tasks.getJSONObject(j).getString("title"),
                            completion = tasks.getJSONObject(j).getBoolean("completion"),
                            achievementTaskType = tasks.getJSONObject(j).getString("taskType")
                        )
                    )
                }
                list.add(
                    Achievement(
                        title = title,
                        color = color,
                        percentage = percentage,
                        achievementType = acheivementType,
                        order = order,
                        isForceLocked = lock,
                        tasks = tasksList
                    )
                )
            }
            state.loading = false
            achievementsResponse.value = list
        }

    }

    private fun loadTransactionFromJsonAssets(context: Context): String? {
        val json: String?
        try {
            val `is` = context.assets.open("yapachievement.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
