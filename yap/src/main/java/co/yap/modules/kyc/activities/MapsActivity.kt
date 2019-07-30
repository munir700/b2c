package co.yap.modules.kyc.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.View
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.R
import co.yap.yapcore.defaults.DefaultActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.layout_marker.*
import java.io.IOException


class MapsActivity : DefaultActivity(), OnMapReadyCallback {
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null

    val RQ_USER_ADDRESS = 3
    private val LOC_REQ_CODE = 1

//    private var mLocationManager: LocationManager? = null

    internal var locationTitle = "Location"
    internal var locationAddress = "LocationAddress"

    protected var mGeoDataClient: GeoDataClient? = null
    private var mCurrentLatLng = LatLng(0.0, 0.0)
    private var mUpdatedLatLng = LatLng(0.0, 0.0)
    internal var PERMISSION_ALL = 1

    private var mMap: GoogleMap? = null
    private val CAMERA_ZOOM = 18


    private lateinit var viewDataBinding: ViewDataBinding


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MapsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        viewDataBinding.setVariable(BR.accountSelection, "")
        viewDataBinding.executePendingBindings()


        mGeoDataClient = Places.getGeoDataClient(this)
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//            .addConnectionCallbacks(this)
//            .addOnConnectionFailedListener(this)
//            .addApi(LocationServices.API)
//            .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mCurrentLatLng = LatLng(0.0, 0.0)

    }
//    @OnClick(R.id.button_confirm)
//    internal fun onConfirmLocation() {
//        startActivity(
//            AddAddressActivity.getLaunchIntent(
//                this@MapsActivity,
//                mUpdatedLatLng.latitude, mUpdatedLatLng.longitude
//            )
//        )
//    }


    override fun onResume() {

        super.onResume()
        if (Build.VERSION.SDK_INT < 23) {
            setUpMapLocation()

        } else {
            requestLocationAccessPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RQ_USER_ADDRESS) {
            if (data != null) {
//                val userAddress = data.getParcelableExtra<Parcelable>(ConstantUtils.EXTRA_USER_ADDRESS)
//                mCurrentLatLng = LatLng(userAddress.getLat(), userAddress.getLng())
                mMap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, CAMERA_ZOOM.toFloat()),
                    2000,
                    null
                )
                getCurrentAddress(mCurrentLatLng)
            }

        }
    }


    fun setUpMapLocation() {

        if (mUpdatedLatLng.latitude == 0.0 && mUpdatedLatLng.longitude == 0.0) {
//            mLocationManager!!.onResume()
        }
        if (mCurrentLatLng.latitude == 0.0 && mCurrentLatLng.longitude == 0.0) {
            mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            mLocationManager!!.init()
            mCurrentLatLng = LatLng(0.0, 0.0)
            initializeMap()
            subscribeLocationUpdates()
        }
    }


    fun requestLocationAccessPermission() {

         val PERMISSIONS =
             arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

         if (!hasPermissions(this, *PERMISSIONS)) {
             ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
         } else {
             setUpMapLocation()

         }
     }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap == null) {
            return
        }
        mMap = googleMap


        mMap!!.isMyLocationEnabled = true
        mMap!!.uiSettings.isMyLocationButtonEnabled = true

        mMap!!.setOnCameraIdleListener {
            card_progress!!.visibility = View.VISIBLE
            val projection = mMap!!.projection


            val imageParentWidth = progress_container!!.width
            val imageParentHeight = progress_container!!.height
            val imageHeight = image_marker!!.height

            val centerX = imageParentWidth / 2
            val centerY = imageParentHeight / 2 + imageHeight / 2


            val centerLatLng = projection.fromScreenLocation(Point(centerX, centerY))
            Handler().postDelayed({ getCurrentAddress(centerLatLng) }, 1000)


        }


    }

    fun initializeMap() {
        val mapFragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    @SuppressLint("CheckResult")
    fun subscribeLocationUpdates() {

//        mLocationManager!!.subscribeLocationUpdate().subscribeWith(object : Observer<Location>() {
//
//            fun onSubscribe(@NonNull d: Disposable) {}
//
//            fun onNext(@NonNull location: Location) {
//                if (mCurrentLatLng.latitude == 0.0 && mCurrentLatLng.longitude == 0.0) {
//                    val lat = location.latitude
//                    val lng = location.longitude
//
//                    mCurrentLatLng = LatLng(lat, lng)
//                    mMap!!.animateCamera(
//                        CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, CAMERA_ZOOM.toFloat()),
//                        2000,
//                        null
//                    )
//                    getCurrentAddress(mCurrentLatLng)
//
//                }
//            }
//
//            fun onError(@NonNull e: Throwable) {}
//
//            fun onComplete() {}
//        })
    }

    fun getCurrentAddress(latLng: LatLng) {
        mCurrentLatLng = latLng
        mUpdatedLatLng = latLng

        val geocoder = Geocoder(this)
        val latitude = latLng.latitude
        val longitude = latLng.longitude

        var addresses: List<Address>? = null
        val addressText: String?
        val addressHeader: String?
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (addresses != null && addresses.size > 0) {
            val address = addresses[0]

            addressText = address.getAddressLine(0)
            addressHeader = address.subLocality
            locationTitle = addressHeader ?: ""
            locationAddress = addressText ?: ""

        }
        setAddress()

    }

    fun setAddress() {
        activity_pick_current_location_name!!.text = ""
        activity_pick_current_location_address!!.text = ""
        activity_pick_current_location_name!!.text = locationTitle
        activity_pick_current_location_address!!.text = locationAddress
        card_progress!!.visibility = View.GONE
    }



    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

}
