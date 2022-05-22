package com.yap.updatemanager

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

const val NO_UPDATE = -1

/**
 * This class is just a wrapper for [AppUpdateInfo] and [InstallState]
 * Used by InAppUpdateManager
 */
data class InAppUpdateStatus(
    var appUpdateInfo: AppUpdateInfo? = null,
    var appUpdateState: InstallState? = null
) {

    val availableVersionCode: Int
        get() = appUpdateInfo?.availableVersionCode() ?: NO_UPDATE

    /**
     * Checks if an update is in progress
     */
    val isUpdateInProgress: Boolean
        get() = appUpdateState != null

    val isDownloading: Boolean
        get() = appUpdateState?.installStatus() == InstallStatus.DOWNLOADING

    val isDownloaded: Boolean
        get() = appUpdateState?.installStatus() == InstallStatus.DOWNLOADED

    /**
     * Checks if an update is available
     */
    fun isUpdateAvailable() =
        appUpdateInfo?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
    /**
     * Checks if an update type allowed @see [InAppUpdateManager.InAppUpdateType]
     */
    fun isUpdateTypeAllowed(@InAppUpdateManager.InAppUpdateType updateType: Int) =
        appUpdateInfo?.isUpdateTypeAllowed(updateType)?:false

    /**
     * Checks if an update is pending -> downloading/downloaded but not installed
     */
    fun isUpdatePending() =
        appUpdateInfo?.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
}