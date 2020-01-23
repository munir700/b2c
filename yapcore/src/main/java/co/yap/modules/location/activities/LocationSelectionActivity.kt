package co.yap.modules.location.activities

import android.Manifest
import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import co.yap.modules.location.helper.MapSupportActivity
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.ADDRESS
import co.yap.yapcore.constants.Constants.ADDRESS_SUCCESS
import co.yap.yapcore.helpers.PermissionHelper
import co.yap.yapcore.helpers.Utils
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_address_selection.*
import kotlinx.android.synthetic.main.layout_google_maps.*

class LocationSelectionActivity : MapSupportActivity(), ILocationSelection.View {

    companion object {
        private const val HEADING = "heading"
        private const val SUB_HEADING = "subHeading"
        fun newIntent(
            context: Context,
            address: Address,
            headingTitle: String = "",
            subHeadingTitle: String = ""
        ): Intent {
            val intent = Intent(context, LocationSelectionActivity::class.java)
            intent.putExtra(HEADING, headingTitle)
            intent.putExtra(SUB_HEADING, subHeadingTitle)
            intent.putExtra(ADDRESS, address)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        checkPermission()
        settAddressFromIntent()
        updateHeadings()
        flTitle.setOnTouchListener { _, _ -> true }
        lyAddressFields.setOnTouchListener { _, _ -> true }
        transparentImage.setOnTouchListener { _, _ -> !((viewModel.isMapExpanded.value) ?: false) }
    }

    private fun settAddressFromIntent() {
        if (intent != null && intent.hasExtra(ADDRESS)) {
            val data: Address? = intent.getParcelableExtra<Address?>(ADDRESS)
            data?.let {
                viewModel.address = it
                viewModel.state.placeTitle.set(it.address1)
                viewModel.state.placeSubTitle.set(it.address2)
            }
        }
    }

    private fun updateHeadings() {
        if (intent != null) {
            if (intent.hasExtra(HEADING)) {
                val heading = intent.getStringExtra(HEADING)
                viewModel.state.headingTitle.set(heading)
            }
            if (intent.hasExtra(SUB_HEADING)) {
                val subHeading = intent.getStringExtra(SUB_HEADING)
                viewModel.state.subHeadingTitle.set(subHeading)
            }
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.isTermsChecked.addOnPropertyChangedCallback(stateObserver)
        viewModel.isMapExpanded.observe(this, Observer {
            viewModel.state.toolbarVisibility = !it
            if (it) {
                rlCollapsedMapSection.visibility = View.GONE
                lyAddressFields.visibility = View.GONE
            } else {
                rlCollapsedMapSection.visibility = View.VISIBLE
                lyAddressFields.visibility = View.VISIBLE
            }
        })
    }

    private fun initMapFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.let {
            it.getMapAsync { googleMap ->
                googleMap?.let { map ->
                    onMapReady(map)
                }
            }
        }
    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            viewModel.state.valid.set(viewModel.state.addressTitle.isNotBlank() && viewModel.state.isTermsChecked.get() == true)
        }
    }
    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                setIntentAction(true)
            }

            R.id.btnLocation -> {
                expandMap()
            }

            R.id.ivClose -> {
                settAddressFromIntent() // set initial address
                if (viewModel.state.isShowLocationCard.get() == true)
                    slideDownCardAnimation()
                else {
                    collapseMap()
                }
            }

            R.id.btnConfirm -> {
                startAnimateLocationCard()
            }
            R.id.tvTermsAndConditions ->{
                Utils.openWebPage(Constants.URL_TERMS_CONDITION, "", this)
            }
            R.id.etAddressField -> {

            }
            R.id.rlCollapsedMapSection -> {
                expandMap()
            }
            R.id.tbIvClose -> {
                setIntentAction(false)
            }
        }
    }


    private fun expandMap() {
        viewModel.isMapExpanded.value = true
        YoYo.with(Techniques.FadeOut)
            .duration(200)
            .playOn(btnLocation)

        YoYo.with(Techniques.SlideOutUp)
            .duration(600)
            .playOn(flTitle)

        YoYo.with(Techniques.SlideOutDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.state.isShowLocationCard.set(true)
                    viewModel.lastKnowLocation.value?.let {
                        animateCameraToLocation(it)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {

                }
            })
            .duration(600)
            .playOn(flAddressDetail)
    }

    private fun collapseMap() {
        viewModel.isMapExpanded.value = false

        YoYo.with(Techniques.FadeIn)
            .duration(400)
            .playOn(btnLocation)

        YoYo.with(Techniques.SlideInDown)
            .duration(400)
            .playOn(flTitle)

        YoYo.with(Techniques.SlideInUp)
            .duration(400)
            .playOn(flAddressDetail)

    }

    private fun startAnimateLocationCard() {
        YoYo.with(Techniques.SlideOutDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.state.isShowLocationCard.set(false)
                    collapseMap()
                    viewModel.onLocationSelected()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(300)
            .playOn(cvLocationCard)
    }

    private fun slideDownCardAnimation() {
        YoYo.with(Techniques.SlideOutDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.state.isShowLocationCard.set(false)
                    collapseMap()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(300)
            .playOn(cvLocationCard)
    }

    private fun setIntentAction(isUpdated: Boolean) {
        val intent = Intent()
        viewModel.address?.address1 = viewModel.state.addressTitle
        viewModel.address?.address2 = viewModel.state.addressSubtitle.get()
        intent.putExtra(ADDRESS, viewModel.address)
        intent.putExtra(ADDRESS_SUCCESS, isUpdated)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                initMapFragment()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")

            }

            override fun onPermissionDeniedBySystem() {
                showToast("Can't proceed without permissions")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionHelper != null) {
            permissionHelper!!.onRequestPermissionsResult(
                requestCode,
                permissions as Array<String>,
                grantResults
            )
        }
    }

    override fun onBackPressed() {
        if (viewModel.isMapExpanded.value == true) {
            slideDownCardAnimation()
        } else
            super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
        viewModel.isMapExpanded.removeObservers(this)
    }
}