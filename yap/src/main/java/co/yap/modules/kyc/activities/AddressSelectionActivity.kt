package co.yap.modules.kyc.activities

import android.Manifest
import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.viewmodels.AddressSelectionViewModel
import co.yap.yapcore.BaseBindingActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.layout_maps.*

class AddressSelectionActivity : BaseBindingActivity<IAddressSelection.ViewModel>(),
    OnMapReadyCallback {

    val REQUEST_CHECK_SETTINGS = 100


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddressSelectionActivity::class.java)
    }


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_map_detail

    override val viewModel: IAddressSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AddressSelectionViewModel::class.java)


    lateinit var icon: BitmapDescriptor
    private var mLocationPermissionGranted: Boolean = false
    private var isLocationSettingsDialogue: Boolean = false

    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel!!.mapDetailViewActivity = AddressSelectionActivity()
        displayLocationSettingsRequest(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
//        clickEvent.setValue(GPS_CLICK_EEVENT)

        transparentImage!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                if (!viewModel.state.isMapOnScreen) {
                    return true

                } else {
                    return false
                }
            }
        })

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnLocation -> {

                    if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        mLocationPermissionGranted = true
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
                            val uri: Uri = Uri.fromParts("package", this.packageName, null)
                            intent.data = uri;
                            this.startActivity(intent);

                        } else {
                            requestPermissions()
                        }
                    } else {
                        displayLocationSettingsRequest(this)
                        expandMap()
                    }
                }

                R.id.btnConfirm -> {
                    slideDownLocationCard()
                }

                R.id.ivClose -> {
                    viewModel.state.isMapOnScreen = false

                    if (viewModel.state.errorChecked) {
                        viewModel.state.cardView = false
                        YoYo.with(Techniques.SlideOutDown)
                            .withListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator?) {
                                }

                                override fun onAnimationRepeat(animation: Animator?) {
                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    collapseMap()
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                }
                            })
                            .duration(300)
                            .playOn(findViewById(R.id.cvLocationCard))
                    } else {
                        collapseMap()
                    }
//                    viewModel.state.cardView = false
//                    viewModel.state.errorVisibility = GONE
//                    YoYo.with(Techniques.SlideOutDown)
//                        .withListener(object : Animator.AnimatorListener {
//                            override fun onAnimationStart(animation: Animator?) {
//                            }
//
//                            override fun onAnimationRepeat(animation: Animator?) {
//                            }
//
//                            override fun onAnimationEnd(animation: Animator?) {
//                                collapseMap()
//                            }
//
//                            override fun onAnimationCancel(animation: Animator?) {
//                            }
//                        })
//                        .duration(300)
//                        .playOn(findViewById(R.id.cvLocationCard))

                }

                R.id.next_button -> {
//goto next screen or api call
                    if (!viewModel.state.error.isNullOrEmpty()) {
                        showToast(viewModel.state.error)

                    }
                }

                viewModel.MARKER_CLICK_ID -> {

                }

                viewModel.GPS_CLICK_EEVENT -> {
                    displayLocationSettingsRequest(this)

                }
            }
        })
    }


    fun displayLocationSettingsRequest(context: Context) {
        if (!isLocationSettingsDialogue) {
//            isLocationSettingsDialogue = true
            val googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build()
            googleApiClient.connect()

            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10000
            locationRequest.fastestInterval = (10000 / 2).toLong()

            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)

            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
                override fun onResult(result: LocationSettingsResult) {
                    val status = result.status
                    when (status.statusCode) {
                        LocationSettingsStatusCodes.SUCCESS -> {
                            viewModel.checkGps = true
                        }

                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                isLocationSettingsDialogue = true

                                viewModel.checkGps = false
                                status.startResolutionForResult(this@AddressSelectionActivity, REQUEST_CHECK_SETTINGS)
                            } catch (e: IntentSender.SendIntentException) {
                                Log.i("TAGAddress", "PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(

                            "TAGAddress",
                            "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                        )
                    }
                }
            })
        }

    }

    override fun onPermissionGranted(permission: String?) {
        super.onPermissionGranted(permission)
        if (mLocationPermissionGranted) {
            expandMap()

        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        viewModel.onMapInit(p0)
    }

    private fun slideDownLocationCard() {

        YoYo.with(Techniques.SlideOutDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    collapseMap()
                    viewModel.onLocatioenSelected()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(300)
            .playOn(findViewById(R.id.cvLocationCard))
    }

    private fun expandMap() {
        viewModel.state.cardView = false
        if (viewModel.checkGps) {
            viewModel.state.isMapOnScreen = true
        } else {
            viewModel.state.isMapOnScreen = false
            viewModel.state.cardView = false
        }

        viewModel.toggleMarkerVisibility()
        YoYo.with(Techniques.FadeOut)
            .duration(200)
            .playOn(findViewById(R.id.btnLocation));

        YoYo.with(Techniques.SlideOutUp)
            .duration(600)
            .playOn(findViewById(R.id.flTitle));

        YoYo.with(Techniques.SlideOutDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.getDeviceLocation()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(600)
            .playOn(findViewById(R.id.flAddressDetail))
    }

    private fun collapseMap() {
        viewModel.state.isMapOnScreen = false
        viewModel.toggleMarkerVisibility()
        if (viewModel.state.errorChecked) {
//            viewModel.state.isMapOnScreen = false
            viewModel.state.cardView = false
        }
//        viewModel.state.cardView = false
        viewModel.state.closeCard = false

        YoYo.with(Techniques.FadeIn)
            .duration(400)
            .playOn(findViewById(R.id.btnLocation));

        YoYo.with(Techniques.SlideInDown)
            .duration(400)
            .playOn(findViewById(R.id.flTitle));

        YoYo.with(Techniques.SlideInUp)
            .duration(400)
            .playOn(findViewById(R.id.flAddressDetail))

        viewModel.toggleMarkerVisibility()

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        isLocationSettingsDialogue = false
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            viewModel.getDeviceLocation()
        }
    }
}