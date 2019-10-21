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
class ExposureLock : BaseLock() {

    override fun checkIsSupported(holder: ActionHolder): Boolean {
        val isNotLegacy = readCharacteristic(
                CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL, -1) != CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY
        // Not sure we should check aeMode as well, probably all aeModes support locking,
        // but this should not be a big issue since we're not even using different AE modes.
        val aeMode = holder.getBuilder(this).get(CaptureRequest.CONTROL_AE_MODE)
        val isAEOn = aeMode != null && (aeMode == CameraCharacteristics.CONTROL_AE_MODE_ON
                || aeMode == CameraCharacteristics.CONTROL_AE_MODE_ON_ALWAYS_FLASH
                || aeMode == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH
                || aeMode == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE
                || aeMode == 5)/* CameraCharacteristics.CONTROL_AE_MODE_ON_EXTERNAL_FLASH, API 28 */
        val result = isNotLegacy && isAEOn
        LOG.i("checkIsSupported:", result)
        return result
    }

    override fun checkShouldSkip(holder: ActionHolder): Boolean {
        val aeState = holder.getLastResult(this).get(CaptureResult.CONTROL_AE_STATE)
        val result = aeState != null && aeState == CaptureResult.CONTROL_AE_STATE_LOCKED
        LOG.i("checkShouldSkip:", result)
        return result
    }

    override fun onStarted(holder: ActionHolder) {
        val cancelTrigger = if (Build.VERSION.SDK_INT >= 23)
            CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_CANCEL
        else
            CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_IDLE
        holder.getBuilder(this).set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
                cancelTrigger)
        holder.getBuilder(this).set(CaptureRequest.CONTROL_AE_LOCK, true)
        holder.applyBuilder(this)
    }

    override fun onCaptureCompleted(holder: ActionHolder,
                                    request: CaptureRequest,
                                    result: TotalCaptureResult) {
        super.onCaptureCompleted(holder, request, result)
        val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
        LOG.i("processCapture:", "aeState:", aeState)
        if (aeState == null) return
        when (aeState) {
            CaptureRequest.CONTROL_AE_STATE_LOCKED -> {
                state = Action.STATE_COMPLETED
            }
            CaptureRequest.CONTROL_AE_STATE_PRECAPTURE, CaptureRequest.CONTROL_AE_STATE_CONVERGED, CaptureRequest.CONTROL_AE_STATE_INACTIVE, CaptureRequest.CONTROL_AE_STATE_SEARCHING, CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED -> {
            }// Wait...
        }
    }

    companion object {

        private val TAG = ExposureLock::class.java.simpleName
        private val LOG = CameraLogger.create(TAG)
    }
}
