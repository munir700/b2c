package com.yap.updatemanager

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

/**
 * This class is just  for [AppUpdateInfo] and [InstallState] helper methods
 * Used by InAppUpdateManager
 */
private const val DAYS_FOR_FLEXIBLE_UPDATE = 4
fun AppUpdateInfo?.availableVersionCode(): Int = this?.availableVersionCode() ?: NO_UPDATE

///**
// * Checks if an update is in progress
// */
//val isUpdateInProgress: Boolean
//    get() = appUpdateState != null

fun InstallState?.isDownloading() = this?.installStatus() == InstallStatus.DOWNLOADING

fun InstallState?.isDownloaded() = this?.installStatus() == InstallStatus.DOWNLOADED
fun AppUpdateInfo?.isDownloaded() = this?.installStatus() == InstallStatus.DOWNLOADED

/**
 * Checks if an update is available
 */
fun AppUpdateInfo?.isUpdateAvailable() =
    this?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE

/**
 * Checks whether the platform allows the specified type of update and current version staleness.
 * you might wait a few days before notifying the user with a flexible update, and a few days after that before requiring an immediate update.
 */
fun AppUpdateInfo?.isUpdateAvailableAfterDays(days: Int = 4) =
    this?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && (this.clientVersionStalenessDays() ?: -1) >= days
            && this.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

/**
 * Checks if an update type allowed @see [InAppUpdateManager.InAppUpdateType]
 */
fun AppUpdateInfo?.isUpdateTypeAllowed(@InAppUpdateManager.InAppUpdateType updateType: Int) =
    this?.isUpdateTypeAllowed(updateType) ?: false

/**
 * Checks if an update is pending -> downloading/downloaded but not installed
 */
fun AppUpdateInfo?.isUpdatePending() =
    this?.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS