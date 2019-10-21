package com.digitify.identityscanner.camera.engine.action

import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * An [Action] that wraps another, and forces the completion
 * after the given timeout in milliseconds is reached.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal class TimeoutAction(private val timeoutMillis: Long, override val action: BaseAction) : ActionWrapper() {

    private var startMillis: Long = 0

    override fun onStart(holder: ActionHolder) {
        startMillis = System.currentTimeMillis()
        super.onStart(holder)
    }

    override fun onCaptureCompleted(holder: ActionHolder,
                                    request: CaptureRequest,
                                    result: TotalCaptureResult) {
        super.onCaptureCompleted(holder, request, result)
        if (!isCompleted) {
            if (System.currentTimeMillis() > startMillis + timeoutMillis) {
                // This will set our state to COMPLETED and stop requests.
                action.abort(holder)
            }
        }
    }
}
