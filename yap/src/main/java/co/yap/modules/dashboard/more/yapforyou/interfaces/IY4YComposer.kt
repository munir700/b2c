package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.networking.transactions.responsedtos.achievement.Achievement

interface IY4YComposer {
    fun compose(achievementsList: ArrayList<Achievement>)
}