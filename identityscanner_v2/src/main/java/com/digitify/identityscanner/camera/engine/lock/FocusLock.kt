package com.digitify.identityscanner.camera.engine.lock

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.os.Build
import androidx.annotation.RequiresApi

import com.digitify.identityscanner.camera.CameraLogger
import com.digitify.identityscanner.camera.engine.action.Action
import com.digitify.identityscanner.camera.engine.action.ActionHolder

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class FocusLock : BaseLock() {

    override fun checkIsSupported(holder: ActionHolder): Boolean {
        // We'll lock by changing the AF mode to AUTO.
        // In that mode, AF won't change unless someone starts a trigger operation.
        val modes = readCharacteristic(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES,
                intArrayOf())
        for (mode in modes) {
            if (mode == CameraCharacteristics.CONTROL_AF_MODE_AUTO) {
                return true
            }
        }
        return false
    }

    override fun checkShouldSkip(holder: ActionHolder): Boolean {
        val lastResult = holder.getLastResult(this)
        val afState = lastResult.get(CaptureResult.CONTROL_AF_STATE)
        val afStateOk = afState != null && (afState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED
                || afState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED
                || afState == CaptureResult.CONTROL_AF_STATE_INACTIVE
                || afState == CaptureResult.CONTROL_AF_STATE_PASSIVE_FOCUSED
                || afState == CaptureResult.CONTROL_AF_STATE_PASSIVE_UNFOCUSED)
        val afMode = lastResult.get(CaptureResult.CONTROL_AF_MODE)
        val afModeOk = afMode != null && afMode == CaptureResult.CONTROL_AF_MODE_AUTO
        val result = afStateOk && afModeOk
        LOG.i("checkShouldSkip:", result)
        return result
    }

    override fun onStarted(holder: ActionHolder) {
        holder.getBuilder(this).set(CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_AUTO)
        holder.getBuilder(this).set(CaptureRequest.CONTROL_AF_TRIGGER,
                CaptureRequest.CONTROL_AF_TRIGGER_CANCEL)
        holder.applyBuilder(this)
    }

    override fun onCaptureCompleted(holder: ActionHolder,
                                    request: CaptureRequest,
                                    result: TotalCaptureResult) {
        super.onCaptureCompleted(holder, request, result)
        val afState = result.get(CaptureResult.CONTROL_AF_STATE)
        val afMode = result.get(CaptureResult.CONTROL_AF_MODE)
        LOG.i("onCapture:", "afState:", afState, "afMode:", afMode)
        if (afState == null || afMode == null) return
        if (afMode != CaptureResult.CONTROL_AF_MODE_AUTO) return
        when (afState) {
            CaptureRequest.CONTROL_AF_STATE_FOCUSED_LOCKED, CaptureRequest.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED, CaptureRequest.CONTROL_AF_STATE_INACTIVE, CaptureRequest.CONTROL_AF_STATE_PASSIVE_FOCUSED, CaptureRequest.CONTROL_AF_STATE_PASSIVE_UNFOCUSED -> {
                state = Action.STATE_COMPLETED
            }
            CaptureRequest.CONTROL_AF_STATE_ACTIVE_SCAN, CaptureRequest.CONTROL_AF_STATE_PASSIVE_SCAN -> {
            }// Wait...
        }
    }

    companion object {

        private val TAG = FocusLock::class.java.simpleName
        private val LOG = CameraLogger.create(TAG)
    }
}
