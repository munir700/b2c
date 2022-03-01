package com.yap.uikit.widgets.dialerpad

interface OnSecureCodeListener {
    fun onCodeCompleted(code: String?,isCompleted:Boolean) {}
    fun onCodeChange(code: String?) {}
}