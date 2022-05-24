package com.yap.updatemanager

import android.app.Activity
import android.content.IntentSender.SendIntentException
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType

class InAppUpdateManager constructor(
    owner: LifecycleOwner,
    private val fragment: Fragment,
) : LifecycleObserver {
    private val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(fragment.requireContext())
    private val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    private var mLifecycle: Lifecycle? = null


    //    var currentInAppUpdateStatus = InAppUpdateStatus()
    private var flexibleUpdateDownloadListener: FlexibleUpdateDownloadListener? = null

    companion object {
        const val REQUEST_CODE_IN_APP_UPDATE = 1230
        const val FLEXIBLE = AppUpdateType.FLEXIBLE
        const val IMMEDIATE = AppUpdateType.IMMEDIATE
    }

    init {
        mLifecycle?.removeObserver(this)
        mLifecycle = owner.lifecycle
        mLifecycle?.addObserver(this)
    }

    // Default mode is FLEXIBLE
    private var mode = IMMEDIATE


    /**
     * Sets the lifecycle owner for this view. This means you don't need
     * to call [.resume] or [.destroy] at all.
     *
     * @param owner the owner activity or fragment
     * @param updateType set the updateType it will be [AppUpdateType.FLEXIBLE] or AppUpdateType.IMMEDIATE see [InAppUpdateType] [AppUpdateType]
     */
//    fun start(owner: LifecycleOwner, @InAppUpdateType updateType: Int) {
//
//        start(updateType)
//    }

    fun start(@InAppUpdateType updateType: Int) {
        mode = updateType
        if (mode == FLEXIBLE) {
            setUpListener()
        }
        checkForUpdate()
    }

    fun isUpdateAvailable(
        @InAppUpdateType updateType: Int = IMMEDIATE,
        isUpdateAvailability: (Boolean) -> Unit
    ) {
        mode = updateType
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            val canUpdate = appUpdateInfo.isUpdateAvailable() && appUpdateInfo.isUpdateTypeAllowed(
                mode
            )
            isUpdateAvailability.invoke(
                canUpdate
            )
        }
        appUpdateInfoTask.addOnFailureListener {
            isUpdateAvailability.invoke(false)
        }
    }

    fun setOnFlexibleUpdateDownloadListener(listener: FlexibleUpdateDownloadListener) {
        flexibleUpdateDownloadListener = listener
    }

    private fun checkForUpdate() {
        if (mode == FLEXIBLE) {
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.isUpdateAvailableAfterDays(4)
                ) {
                    startUpdate(appUpdateInfo)
                }
            }

        } else {
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.isUpdateAvailable() && appUpdateInfo.isUpdateTypeAllowed(
                        mode
                    )
                ) {
                    startUpdate(appUpdateInfo)
                }
            }
        }
    }

    private fun setUpListener() {
        appUpdateManager.registerListener(listener)
    }

    private val listener =
        InstallStateUpdatedListener { installState ->
            if (installState.isDownloading()) {
                val bytesDownloaded = installState.bytesDownloaded()
                val totalBytesToDownload = installState.totalBytesToDownload()
                flexibleUpdateDownloadListener?.onDownloadProgress(
                    bytesDownloaded,
                    totalBytesToDownload
                )
            }
            if (installState.isDownloaded()) {
                flexibleUpdateDownloadListener?.onDownloadComplete()
            }
        }

    private fun unregisterListener() {
        appUpdateManager.unregisterListener(listener)
    }

    @OnLifecycleEvent(Event.ON_RESUME)
    private fun onResume() {
        continueUpdate()
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    private fun onDestroy() {
        unregisterListener()
    }


    /**
     * Starts an in app update process
     *
     * @param updateType set the type of the in app update
     */
    private fun startUpdate(@InAppUpdateType updateType: Int) {
        // to be saved
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                updateType,
                fragment::startIntentSenderForResult,
                REQUEST_CODE_IN_APP_UPDATE
            )
        }
    }

    /**
     * Starts an in app update process
     *
     * @param appUpdateInfo set the appUpdateInfo of the in app update
     */
    private fun startUpdate(appUpdateInfo: AppUpdateInfo?) {
        appUpdateInfo?.let {
            appUpdateManager.startUpdateFlowForResult(
                it,
                mode,
                fragment::startIntentSenderForResult,
                REQUEST_CODE_IN_APP_UPDATE
            )
        } ?: run {
            startUpdate(mode)
        }

    }

    fun addUpdateInfoListener(updateInfoListener: UpdateInfoListener) {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.isUpdateAvailable()) {
                val availableVersionCode =
                    appUpdateInfo.availableVersionCode()
                val stalenessDays =
                    appUpdateInfo?.clientVersionStalenessDays()
                updateInfoListener.onReceiveVersionCode(availableVersionCode)
                updateInfoListener.onReceiveStalenessDays(stalenessDays)
            }
        }
    }

    private fun continueUpdateForImmediate() {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.isUpdatePending()
            ) {
                // If an in-app update is already running, resume the update.
                try {
                    mode = IMMEDIATE
                    startUpdate(appUpdateInfo)
                } catch (e: SendIntentException) {
                }
            }

        }
    }

    private fun continueUpdateForFlexible() {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.isDownloaded()) {
                flexibleUpdateDownloadListener?.onDownloadComplete()
            }
        }
    }

    private fun continueUpdate() {
        if (mode == AppUpdateType.FLEXIBLE) {
            continueUpdateForFlexible()
        } else {
            continueUpdateForImmediate()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == REQUEST_CODE_IN_APP_UPDATE && resultCode == Activity.RESULT_CANCELED) {
            if (mode == IMMEDIATE)
                startUpdate(IMMEDIATE)
        }
        if (requestCode == REQUEST_CODE_IN_APP_UPDATE && resultCode == Activity.RESULT_OK) {
            continueUpdate()
        }
    }

    /**
     * If a download is complete, start the installation with this method
     */
    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(FLEXIBLE, IMMEDIATE)
    annotation class InAppUpdateType
}
