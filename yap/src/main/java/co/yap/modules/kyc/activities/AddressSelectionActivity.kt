package co.yap.modules.kyc.activities

import android.Manifest
import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor

class AddressSelectionActivity : BaseBindingActivity<IAddressSelection.ViewModel>(),
    OnMapReadyCallback {


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddressSelectionActivity::class.java)
    }


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_map_detail

    override val viewModel: IAddressSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AddressSelectionViewModel::class.java)


    lateinit var icon: BitmapDescriptor
    private var mLocationPermissionGranted: Boolean = false

    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel!!.mapDetailViewActivity = AddressSelectionActivity()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)



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
                        expandMap()

                    }
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

                viewModel.MARKER_CLICK_ID -> {

                }
            }
        })
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
        YoYo.with(Techniques.FadeOut)
            .duration(200)
            .playOn(findViewById(R.id.btnLocation));

        YoYo.with(Techniques.SlideOutUp)
            .duration(600)
            .playOn(findViewById(R.id.flTitle));

        YoYo.with(Techniques.SlideOutDown)
            .duration(600)
            .playOn(findViewById(R.id.flAddressDetail))

        viewModel.state.isMapOnScreen
        viewModel.getDeviceLocation()
//        viewModel.toggleMarkerVisibility()

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

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}