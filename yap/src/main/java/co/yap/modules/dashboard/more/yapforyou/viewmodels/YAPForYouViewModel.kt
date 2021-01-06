package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.dashboard.more.yapforyou.Y4YGraphComposer
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.states.YAPForYouState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.networking.transactions.responsedtos.achievement.AchievementTask
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class YAPForYouViewModel(application: Application) :
    YapForYouBaseViewModel<IYAPForYou.State>(application), IYAPForYou.ViewModel,
    IRepositoryHolder<TransactionsRepository> {

    private val y4yComposer: IY4YComposer = Y4YGraphComposer()
    override val repository: TransactionsRepository = TransactionsRepository
    override val state: YAPForYouState =
        YAPForYouState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adaptor: YAPForYouAdapter = YAPForYouAdapter(mutableListOf())

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        context
        super.onCreate()
        setToolbarData()
    }

    private fun setToolbarData() {
        state.toolbarTitle = getString(Strings.screen_yap_for_you_display_text_title)
        toggleToolBarVisibility(false)
        state.toolbarVisibility.set(true)
    }

    override fun getAchievements() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getAchievements()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.achievementsList =
                            y4yComposer.compose(response.data.data as ArrayList<Achievement>)
                        adaptor.setList(parentViewModel?.achievementsList ?: mutableListOf())
                        state.currentAchievement.set(getCurrentAchievement(parentViewModel?.achievementsList as ArrayList<Y4YAchievementData>))
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun setSelectedAchievement(y4YAchievementData: Y4YAchievementData) {
        parentViewModel?.selectedAchievement = y4YAchievementData
    }

    private fun getCurrentAchievement(from: ArrayList<Y4YAchievementData>): Y4YAchievementData {
        from.sortWith(Comparator { first, second ->
            if (first.completedPercentage > second.completedPercentage && first.lastUpdated > second.lastUpdated) {
                1
            } else {
                0
            }
        })
        return from.first()
    }

    fun getMockApiResponse() {
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
            parentViewModel?.achievementsList =
                y4yComposer.compose(list)
            adaptor.setList(parentViewModel?.achievementsList ?: mutableListOf())
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
