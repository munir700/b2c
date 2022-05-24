package com.yap.updatemanager

interface UpdateInfoListener {
    fun onReceiveVersionCode(code: Int?){}
    fun onReceiveStalenessDays(days: Int?){}
}