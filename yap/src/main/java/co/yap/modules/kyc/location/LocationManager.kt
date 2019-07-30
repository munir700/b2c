package co.yap.modules.kyc.location


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat


import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task

import java.io.IOException
import java.text.DateFormat


import android.app.Activity.RESULT_OK
import android.text.InputType
import java.util.*
import java.util.List

class LocationManager(private val mContext: Context) : ActivityCallBackContract {


      var LOCATION_PERMISSION_REQUEST :Int= 100
      var mLocationRequest: LocationRequest? = null
      val mFusedLocationClient: FusedLocationProviderClient
      var mLocationCallback: LocationCallback? = null
     val REQUEST_CHECK_SETTINGS = 200

//    private var mLocationEmitter: ObservableEmitter<Location>? = null
//    private var mLocationObserable: Observable<Location>? = null

    init {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

    }

    fun init() {

        createLocationRequest()
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                for (location in locationResult!!.locations) {
                    // Update UI with location data
                    // ...
//                    if (location != null) {
////                        LoggerUtil.LogInfo(
////                            ConstantUtils.TAG, "onLocationChanged " +
////                                    DateFormat.getTimeInstance().format(Date()) + " " + location.toString()
//                        )

//                        if (mLocationEmitter != null) {
//                            mLocationEmitter!!.onNext(location)
//                        }

                    }
                }
            }
        }
//    }

    override fun onPause() {
        stopLocationUpdates()
    }

    override   fun onResume() {
        startLocationUpdates()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {


//        when (requestCode) {
//            LOCATION_PERMISSION_REQUEST -> {
//                editText.inputType = InputType.TYPE_CLASS_NUMBER
//                setPhoneNumberField()
//                requestKeyboard()
//            }
//
//
//        }
//


//        val LOCATION_PERMISSION_REQUEST = 100
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    LoggerUtil.LogDebug(ConstantUtils.TAG, "coarse location permission granted")
                    createLocationRequest()
                }
            }
        }
    }


    private fun startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        if (mLocationRequest != null) {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback!!, null/* Looper */
            )
        }

    }

    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback!!)
    }


    private fun createLocationRequest() {

        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )

            return
        }

        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)


        val client = LocationServices.getSettingsClient(mContext)
        val task = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener(mContext as Activity) {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            startLocationUpdates()
        }

        task.addOnFailureListener(mContext) { e ->
            val statusCode = (e as ApiException).statusCode
            when (statusCode) {
                CommonStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        val resolvable = e as ResolvableApiException
                        resolvable.startResolutionForResult(mContext, REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }// Location settings are not satisfied. However, we have no way
            // to fix the settings so we won't show the dialog.
        }

    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
//                LoggerUtil.LogDebug(ConstantUtils.TAG, "GPS Setting on")
                createLocationRequest()
                startLocationUpdates()

            } else {
//                LoggerUtil.LogDebug(ConstantUtils.TAG, "GPS Setting off")
            }
        }
    }

//    fun subscribeLocationUpdate(): Observable<Location>? {
//
//        if (mLocationObserable == null) {
//            LoggerUtil.LogDebug(ConstantUtils.TAG, "Obserable not set")
//            mLocationObserable = Observable.create(object : ObservableOnSubscribe<Location>() {
//                @Throws(Exception::class)
//                fun subscribe(e: ObservableEmitter<Location>) {
//                    mLocationEmitter = e
//                }
//            }).publish().autoConnect()
//
//        } else {
//            LoggerUtil.LogDebug(ConstantUtils.TAG, "Obserable  set")
//        }
//
//        return mLocationObserable
//    }

//    fun getCurrentLocality(lat: Double, lng: Double): Single<UserAddress> {
//
//        return Single.create(object : SingleOnSubscribe<UserAddress>() {
//            @Throws(Exception::class)
//            fun subscribe(@io.reactivex.annotations.NonNull e: SingleEmitter<UserAddress>) {
//                getCurrentLocality(e, lat, lng)
//            }
//        })
//
//    }


//    private fun getCurrentLocality(e: SingleEmitter<UserAddress>, lat: Double, lng: Double) {
//
//        val addresses: List<Address>?
//        val geocoder = Geocoder(mContext, Locale.US)
//
//        try {
//            addresses = geocoder.getFromLocation(
//                lat,
//                lng,
//                3
//            )
//
//            if (addresses != null && addresses.size > 0) {
//
//                e.onSuccess(formatAddress(addresses))
//
//            } else {
//                //just send empty user address
////                e.onSuccess(UserAddress())
//            }
//
//        } catch (io: IOException) {
////            LoggerUtil.LogError(ConstantUtils.TAG, "user current address  " + io.message)
//            e.onError(Throwable(io))
//        }
//
//    }

    /**
     * Format the possible address and save into database and return the #UserAddress
     *
     * @param addresses
     * @return UserAddress
     */
//    private fun formatAddress(addresses: List<Address>): UserAddress {
//
//        val address = addresses.get(0)
//        val address2 = addresses.get(1)
//        val address3 = addresses.get(2)
//
//        val userAddress = UserAddress()
//        userAddress.setId("12")
//
//
//        //set city
//        if (address.locality != null) {
//            userAddress.setCity(address.locality)
//        } else {
//            userAddress.setCity("")
//        }
//
//
//        //set area if found
//        if (address2.featureName != null) {
//            val areaBuilder = StringBuilder()
//            areaBuilder.append(address2.featureName).append(" ")
//
//            if (address3.featureName != null && !address2.featureName.equals(address3.featureName, ignoreCase = true)) {
//                areaBuilder.append(address3.featureName)
//            }
//
//            userAddress.setArea(areaBuilder.toString())
//
//        } else {
//
//            if (address.featureName != null) {
//                userAddress.setArea(address.featureName)
//            } else {
//                userAddress.setArea("")
//            }
//        }
//
//        //set street address if found
//        if (address.thoroughfare != null) {
//
//            userAddress.setStreetAddress(address.thoroughfare)
//
//        } else {
//
//            if (address2.thoroughfare != null) {
//                userAddress.setStreetAddress(address2.thoroughfare)
//            } else {
//                userAddress.setStreetAddress("")
//            }
//
//        }
//
//        return userAddress
//    }

}
