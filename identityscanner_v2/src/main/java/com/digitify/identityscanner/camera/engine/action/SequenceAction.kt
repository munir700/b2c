package com.digitify.identityscanner.camera.engine.action

import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Executes a list of actions in sequence, completing once
 * the last of them has been completed.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal class SequenceAction(// Need to be BaseAction so we can call onStart() instead of start()
        private val actions: List<BaseAction>) : BaseAction() {
    private var runningAction = -1

    init {
        increaseRunningAction()
    }

    private fun increaseRunningAction() {
        val first = runningAction == -1
        val last = runningAction == actions.size - 1
        if (last) {
            // This was the last action. We're done.
            state = Action.Companion.STATE_COMPLETED
        } else {
            runningAction++
            actions[runningAction].addCallback(object : ActionCallback {
                override fun onActionStateChanged(action: Action, state: Int) {
                    if (state == Action.Companion.STATE_COMPLETED) {
                        action.removeCallback(this)
                        increaseRunningAction()
                    }
                }
            })
            if (!first) {
                actions[runningAction].onStart(holder!!)
            }
        }
    }

    override fun onStart(holder: ActionHolder) {
        super.onStart(holder)
        if (runningAction >= 0) {
            actions[runningAction].onStart(holder)
        }
    }

    override fun onAbort(holder: ActionHolder) {
        super.onAbort(holder)
        if (runningAction >= 0) {
            // Previous actions have been completed already.
            // Future actions will never start. So this is OK.
            actions[runningAction].onAbort(holder)
        }
    }

    override fun onCaptureStarted(holder: ActionHolder, request: CaptureRequest) {
        super.onCaptureStarted(holder, request)
        if (runningAction >= 0) {
            actions[runningAction].onCaptureStarted(holder, request)
        }
    }

    override fun onCaptureProgressed(holder: ActionHolder,
                                     request: CaptureRequest,
                                     result: CaptureResult) {
        super.onCaptureProgressed(holder, request, result)
        if (runningAction >= 0) {
            actions[runningAction].onCaptureProgressed(holder, request, result)
        }
    }

    override fun onCaptureCompleted(holder: ActionHolder,
                                    request: CaptureRequest,
                                    result: TotalCaptureResult) {
        super.onCaptureCompleted(holder, request, result)
        if (runningAction >= 0) {
            actions[runningAction].onCaptureCompleted(holder, request, result)
        }
    }
}
