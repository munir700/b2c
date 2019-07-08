package co.yap.networking.authentication.requestdtos

data class DemographicDataRequest(val action: String, val osVersion: String, val deviceId: String, val deviceName: String, val deviceModel: String, val osType: String)
