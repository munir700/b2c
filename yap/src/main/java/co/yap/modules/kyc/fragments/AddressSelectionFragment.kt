package co.yap.modules.kyc.fragments

import android.Manifest
import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.viewmodels.AddressSelectionViewModel
import co.yap.modules.onboarding.constants.Constants
import co.yap.networking.cards.requestdtos.UpdateAddressRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.yapcore.interfaces.BaseMapFragment
import co.yap.yapcore.managers.MyUserManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_address_selection.*
import kotlinx.android.synthetic.main.layout_maps.*

class AddressSelectionFragment : BaseMapFragment<IAddressSelection.ViewModel>(),
    OnMapReadyCallback {

    val REQUEST_CHECK_SETTINGS = 100


    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddressSelectionFragment::class.java)
    }


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_address_selection

    override val viewModel: IAddressSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AddressSelectionViewModel::class.java)


    lateinit var icon: BitmapDescriptor
    private var locationPermissionGranted: Boolean = false
    private var isLocationSettingsDialogue: Boolean = false

    var placeTitle: String = ""
    var placeSubTitle: String = ""
    var placePhoto: Bitmap? = null
    private lateinit var viewDataBinding: ViewDataBinding
    private var isFromPersonalDetailScreen = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val isFromBlockCardsScreen =
            arguments?.let { AddressSelectionFragmentArgs.fromBundle(it).isFromBlockCardsScreen }

        isFromPersonalDetailScreen =
            arguments?.let { AddressSelectionFragmentArgs.fromBundle(it).isFromPersonalDetail }!!

        val checkSender =
            arguments?.let { AddressSelectionFragmentArgs.fromBundle(it).isFromPhysicalCardsScreen }

        if (isFromPersonalDetailScreen!!) {
            viewModel!!.mapDetailViewActivity = activity as MoreActivity
            viewModel.state.isFromPersonalDetailView = true
            viewModel.state.isFromPhysicalCardsLayout = false
            updateHeadings()

            viewModel.state.nextActionBtnText =
                getString(Strings.idenetity_scanner_sdk_screen_review_info_button_next)

            if (MyUserManager.userAddress != null) {
                setUpAddressFields()
            }
            viewModel.state.subHeadingTitle =
                getString(Strings.screen_meeting_location_display_text_subtitle)

        } else if (isFromBlockCardsScreen!!) {
            viewModel!!.mapDetailViewActivity = activity as ReportLostOrStolenCardActivity
            viewModel.state.isFromPhysicalCardsLayout = true
            updateHeadings()
        } else if (checkSender!!) {
            viewModel!!.mapDetailViewActivity = activity as AddPaymentCardActivity
            viewModel.state.isFromPhysicalCardsLayout = true
            updateHeadings()
        } else {
            viewModel!!.mapDetailViewActivity = activity as DocumentsDashboardActivity

        }
         performDataBinding(inflater, container)
        initMapFragment()

        return viewDataBinding.root
    }

    private fun setUpAddressFields() {
//        var address: Address = MyUserManager.userAddress!!
//
//        var addresstitle = ""
//        var addressDetail = ""
//
//        if (!address.address2.isNullOrEmpty()) {
//            addresstitle = address.address2!!
//        }
//
//        if (!address.address1.isNullOrEmpty()) {
//            addressDetail = address.address1!!
//        }
//
//        addresstitle + " " + addressDetail
//        viewModel.mDefaultLocation = LatLng(
//            MyUserManager.userAddress!!.latitude!!,
//            MyUserManager.userAddress!!.longitude!!
//        )
//
//        viewModel.state.placeTitle = addresstitle
//        viewModel.state.placeSubTitle = addressDetail
//        viewModel.state.addressField = addressDetail
//        viewModel.state.landmarkField = addresstitle


    }

    private fun updateHeadings() {
        viewModel.state.headingTitle =
            getString(Strings.screen_meeting_location_display_text_add_new_address_title)
        viewModel.state.subHeadingTitle =
            getString(Strings.screen_meeting_location_display_text_add_new_address_subtitle)
        viewModel.state.nextActionBtnText =
            getString(Strings.screen_meeting_location_button_confirm_selected_location)
    }

    private fun initMapFragment() {
        displayLocationSettingsRequest(requireContext())
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transparentImage!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                if (!viewModel.state.isMapOnScreen) {
                    return true

                } else {
                    return false
                }
            }
        })

        viewModel.onSuccess.observe(this, Observer {
            when (it) {

                viewModel.UPDATE_ADDRESS_EEVENT -> {
                    val action =
                        AddressSelectionFragmentDirections.actionAddressSelectionFragmentToSuccessFragment(
                            getString(R.string.screen_address_success_display_text_sub_heading),
                            " "
                        )

                    findNavController().navigate(action)
                }

            }
        })

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnLocation -> {
//                    hideKeyboard(mapView)
                    if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        locationPermissionGranted = true
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
                            val uri: Uri =
                                Uri.fromParts("package", requireContext()!!.packageName, null)
                            intent.data = uri
                            this.startActivity(intent)

                        } else {
                            requestPermissions()
                        }
                    } else {
                        requireContext()?.let { it1 -> displayLocationSettingsRequest(it1) }
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
                            .playOn(cvLocationCard)
                    } else {
                        collapseMap()
                    }
                }

                R.id.nextButton -> {
                    if (viewModel.state.isFromPhysicalCardsLayout) {
                        //todo refactor
//                        val action =
//                            AddressSelectionFragmentDirections.actionAddressSelectionFragmentToAddSpareCardFragment(
//                                getString(R.string.screen_spare_card_landing_display_text_physical_card),
//                                viewModel.state.placeTitle,
//                                viewModel.state.placeSubTitle,
//                                viewModel.mDefaultLocation.latitude.toString(),
//                                viewModel.mDefaultLocation.longitude.toString(),
//                                false
//
//                            )
//                        findNavController().navigate(action)


                    } else if (viewModel.state.isFromPersonalDetailView) {
//
//                        viewModel.state.placeTitle = addresstitle
//                        viewModel.state.placeSubTitle = addressDetail
//                        viewModel.state.addressField = addressDetail
//                        viewModel.state.landmarkField = addresstitle
                        var updateAddressRequest: UpdateAddressRequest = UpdateAddressRequest(
                            viewModel.state.addressField,
                            viewModel.state.landmarkField,
                            viewModel.mDefaultLocation.latitude.toString(),
                            viewModel.mDefaultLocation.longitude.toString()
                        )
                        if (!viewModel.state.placeTitle.isNullOrEmpty()) {
                            MyUserManager.userAddress!!.address2 = viewModel.state.placeTitle
                        }

                        if (!viewModel.state.placeSubTitle.isNullOrEmpty()) {
                            MyUserManager.userAddress!!.address1 = viewModel.state.placeSubTitle
                        }

                        viewModel.requestUpdateAddress(updateAddressRequest)

                    } else {
                        if (!viewModel.state.error.isNullOrEmpty()) {
                            showToast(viewModel.state.error)
                        } else {
                            MyUserManager.user?.notificationStatuses =
                                Constants.USER_STATUS_MEETING_SCHEDULED
                            findNavController().navigate(R.id.action_AddressSelectionActivity_to_MeetingConfirmationFragment)
                        }
                    }
                }


                viewModel.ON_ADD_NEW_ADDRESS_EVENT -> {

                    if (viewModel.state.isFromPhysicalCardsLayout) {
                        val action =
                            AddressSelectionFragmentDirections.actionAddressSelectionFragmentToAddSpareCardFragment(
                                getString(R.string.screen_spare_card_landing_display_text_physical_card),
                                viewModel.state.placeTitle,
                                viewModel.state.placeSubTitle,
                                viewModel.mDefaultLocation.latitude.toString(),
                                viewModel.mDefaultLocation.longitude.toString(),
                                false

                            )
                        findNavController().navigate(action)


                    } /*else if (viewModel.state.isFromPersonalDetailView) {
//
//                        viewModel.state.placeTitle = addresstitle
//                        viewModel.state.placeSubTitle = addressDetail
//                        viewModel.state.addressField = addressDetail
//                        viewModel.state.landmarkField = addresstitle
                        var updateAddressRequest: UpdateAddressRequest = UpdateAddressRequest(
                            viewModel.state.addressField,
                            viewModel.state.landmarkField,
                            viewModel.mDefaultLocation.latitude.toString(),
                            viewModel.mDefaultLocation.longitude.toString()
                        )
                        if (!viewModel.state.placeTitle.isNullOrEmpty()) {
                            MyUserManager.userAddress!!.address2 = viewModel.state.placeTitle
                        }

                        if (!viewModel.state.placeSubTitle.isNullOrEmpty()) {
                            MyUserManager.userAddress!!.address1 = viewModel.state.placeSubTitle
                        }

                        viewModel.requestUpdateAddress(updateAddressRequest)

                    }*/ else {
                        if (!viewModel.state.error.isNullOrEmpty()) {
                            showToast(viewModel.state.error)
                        } else {
                            MyUserManager.user?.notificationStatuses =
                                Constants.USER_STATUS_MEETING_SCHEDULED
                            findNavController().navigate(R.id.action_AddressSelectionActivity_to_MeetingConfirmationFragment)
                        }
                    }
                }

                viewModel.GPS_CLICK_EEVENT -> {
                    isLocationSettingsDialogue = false
                    requireContext()?.let { it1 -> displayLocationSettingsRequest(it1) }

                }
            }
        })
    }

    override fun onMapReady(p0: GoogleMap?) {
        viewModel.onMapInit(p0)
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

            val result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
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
                                status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)

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
            .playOn(cvLocationCard)
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_address_selection, container, false)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    private fun expandMap() {
        if (isFromPersonalDetailScreen){
            (context as MoreActivity).goneToolbar()
        }

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
                    viewModel.getDeviceLocation(viewModel!!.mapDetailViewActivity)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(600)
            .playOn(flAddressDetail)
    }

    private fun collapseMap() {
        if (isFromPersonalDetailScreen){
            (context as MoreActivity).visibleToolbar()
        }
        viewModel.state.isMapOnScreen = false
        viewModel.toggleMarkerVisibility()
        if (viewModel.state.errorChecked) {
            viewModel.state.cardView = false
        }
        viewModel.state.closeCard = false

        YoYo.with(Techniques.FadeIn)
            .duration(400)
            .playOn(btnLocation)

        YoYo.with(Techniques.SlideInDown)
            .duration(400)
            .playOn(flTitle)

        YoYo.with(Techniques.SlideInUp)
            .duration(400)
            .playOn(flAddressDetail)

        viewModel.toggleMarkerVisibility()

    }

    override fun onDestroy() {
//        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        isLocationSettingsDialogue = false
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            viewModel.getDeviceLocation(viewModel!!.mapDetailViewActivity)
        }
    }
}