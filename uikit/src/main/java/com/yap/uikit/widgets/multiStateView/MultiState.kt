package com.yap.uikit.widgets.multiStateView

import androidx.annotation.Nullable

class MultiState(
    var status: Status,
    var message: String?
) {

    var hardAlert = false

    override fun toString(): String {
        return "status: $status, message: $message"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val state = other as MultiState
//        other as State

        if (status != state.status) return false
        if (message != state.message) return false
        if (hardAlert != state.hardAlert) return false

        return if (message != null) message == state.message else state.message == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + hardAlert.hashCode()
        return result
    }

    companion object {

        fun loading(message: String? = null): MultiState {
            return MultiState(Status.LOADING, message)
        }

        fun error(message: String? = null): MultiState {
            return MultiState(Status.ERROR, message)
        }

        fun success(@Nullable message: String? = null): MultiState {
            return MultiState(Status.SUCCESS, message)
        }

        fun empty(@Nullable message: String? = null): MultiState {
            return MultiState(Status.EMPTY, message)
        }

        fun network(@Nullable message: String?): MultiState {
            return MultiState(Status.NETWORK, message)
        }

        fun ideal(@Nullable message: String?): MultiState {
            return MultiState(Status.IDEAL, message)
        }
    }
}