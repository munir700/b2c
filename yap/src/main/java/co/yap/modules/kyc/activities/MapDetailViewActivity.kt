package co.yap.modules.kyc.activities

import android.Manifest
import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.viewmodels.AddressSelectionViewModel
import co.yap.yapcore.BaseBindingActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.activity_map_detail.*

class MapDetailViewActivity : BaseBindingActivity<IAddressSelection.ViewModel>(),
    OnMapReadyCallback {


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MapDetailViewActivity::class.java)
    }


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_map_detail

    override val viewModel: IAddressSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AddressSelectionViewModel::class.java)


    lateinit var icon: BitmapDescriptor
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted: Boolean = false

    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel!!.mapDetailViewActivity = MapDetailViewActivity()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)



        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnLocation -> {

                    if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
                            val uri:Uri=Uri.fromParts("package", this.packageName, null)
                            intent.data = uri;
                            this.startActivity(intent);

                        } else {
                            requestPermissions()
                        }
                    } else
//                        onPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
                       expandMap()
                }

                R.id.btnConfirm -> {
                    slideDownLocationCard()
                }

                R.id.ivClose -> {
                    collapseMap()
                }

                R.id.next_button -> {
//goto next screen or api call
                }

                viewModel.PERMISSION_EVENT_ID -> {
                    getLocationPermission()
                }

                viewModel.MARKER_CLICK_ID -> {

                }
            }
        })
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
            .duration(400)
            .playOn(findViewById(R.id.cvLocationCard))


    }

    private fun expandMap() {
        YoYo.with(Techniques.FadeOut)
            .duration(100)
            .playOn(findViewById(R.id.btnLocation));

        YoYo.with(Techniques.SlideOutUp)
            .duration(400)
            .playOn(findViewById(R.id.flTitle));

        YoYo.with(Techniques.SlideOutDown)
            .duration(400)
            .playOn(findViewById(R.id.flAddressDetail))
    }

    private fun collapseMap() {
        viewModel.state.isMapOnScreen = false
        viewModel.state.cardView = false
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

        //        viewModel.onLocatioenSelected()
    }

    /**
     * Prompts the user for permission to use the device location.
     */

    fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                    viewModel.getDeviceLocation()
                }
            }
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }


}



