package co.yap.modules.onboarding.fragments

import com.digitify.identityscanner.fragments.BaseFragment


class LocationSelectionActivity : BaseFragment() {
    override fun getScreenTitle(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//
//    internal var tvAddressHint: TextView
//    internal var tvAddressTwoHint: TextView
//    internal var tvSearchAddress: TextView
//    internal var tvAddress: TextView
//    internal var etAddressTwo: EditText
//    internal var btnNext: Button
//    internal lateinit var alertDialog: AlertDialog
//     internal lateinit var geocoder: Geocoder
//    internal lateinit var mapsClass: MapsUtil
//     internal lateinit var inflater: LayoutInflater
//    internal var mMap: GoogleMap? = null
//    internal lateinit var mapFragment: SupportMapFragment
//    internal lateinit var scrollView: ScrollView
//    internal var address: String? = null
//    internal var accountType: String? = null
//    internal var cardName: String? = null
//    internal var companyName: String? = null
//    internal var isSoleProprietary: Boolean = false
//    internal var isPhysical: Boolean = false
//    internal var isAlreadyVirtual: Boolean = false
//    internal var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1122
//
//
//
//
//
////    private fun onCreate(savedInstanceState: Bundle) {
////        super.onCreate(savedInstanceState)
////
////
////        initView()
////        initObj()
////        initListeners()
//////        checkPermissions()
////        topLevelFocus(activity)
////
////    }
//
//
//    private fun initView() {
//
////        rlLocation.tvHeading.setVisibility(View.GONE)
////        rlLocation.tvSubHeading.setVisibility(View.GONE)
//
////        mapFragment = getSupportFragmentManager()
//        mapFragment = fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
//        assert(getFragmentManager() != null)
//        (fragmentManager!!.findFragmentById(R.id.map) as MapDetailViewFragment)
//            .setListener({ scrollView.requestDisallowInterceptTouchEvent(true) })
//
//    }
//
//    private fun initViewForBusiness() {
////        tvSearchAddress.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_search_address))
////        tvAddressHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address))
////        tvAddressTwoHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address_two))
////        btnNext.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_button_text_next))
//    }
//
//    private fun initViewForConsumer() {
////        tvSearchAddress.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_search_address))
////        tvAddressHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address))
////        tvAddressTwoHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address_two))
////        btnNext.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_button_text_next))
//    }
//
//    private fun initViewForSubCard() {
////        tvSearchAddress.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_search_address))
////        tvAddressHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address))
////        tvAddressTwoHint.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_address_two))
////        btnNext.setText(Translator.getInstance().getString(TraslatorKeys.setup_meeting_button_text_next))
//    }
//
//
//    private fun initListeners() {
////        btnNext.setOnClickListener(mGlobal_OnClickListener)
////        tvAddress.setOnClickListener(mGlobal_OnClickListener)
//    }
//
//    private fun scheduleMeeting() {
////         locationSelectionViewModel.scheduleMeetingRequest(object : MyCallBack() {
////            fun onSuccess(responseDTO: ResponseDTO) {
////                hideProgressBar()
////                locationSelectionViewModel.navigate(DebitLocationSelectionViewModel(locationSelectionViewModel))
////            }
////
////            fun onFailure(resDTO: ResponseDTO) {
////                hideProgressBar()
////                if (null != resDTO.getErrors()) {
////                    try {
////                        for (i in 0 until resDTO.getErrors().size()) {
////                            if (Constants.CODE_CARD_ALREADY_ORDERED.equals(resDTO.getErrors().get(i).getCode()) || Constants.CODE_CARD_ALREADY_ORDERED_TWO.equals(
////                                    resDTO.getErrors().get(i).getCode()
////                                )
////                            ) {
////                                locationSelectionViewModel.navigate(
////                                    DebitLocationSelectionViewModel(
////                                        locationSelectionViewModel
////                                    )
////                                )
////                            } else {
////                                showToast(resDTO.getErrors().get(i).getMessage())
////                            }
////                        }
////                    } catch (e: Exception) {
////                        e.printStackTrace()
////                    }
////
////                }
////            }
////        })
//    }
//
//    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        val permissionFineLocation = ContextCompat.checkSelfPermission(
//            Objects.requireNonNull(activity),
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//        if (permissionFineLocation == PackageManager.PERMISSION_GRANTED) {
//            setLocation()
//        }
//    }
//
//    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                mapsClass.setMarkerSearchLocation(data)
//                address = mapsClass.getAddress()
//                tvAddress.text = address
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//
//            } else if (resultCode == RESULT_CANCELED) {
//            }
//        } else if (requestCode == Constants.PERSONAL_REQUEST_CHECK_SETTINGS_GPS) {
//            if (resultCode == RESULT_OK) {
//                setLocation()
//            } else if (resultCode == RESULT_CANCELED) {
//                mMap!!.uiSettings.isMyLocationButtonEnabled = false
//            }
//
//        }
//    }
//
//    private fun initObj() {
//
//        val bundle = getIntent().getExtras()
//        var uuid: String? = ""
//        var customerUuid: String? = ""
//        if (null != bundle) {
//            accountType = bundle!!.getString(Constants.KEY_ACCOUNT_TYPE)
//            isSoleProprietary = bundle!!.getBoolean(Constants.KEY_SOLE_PROPRIETARY, false)
//            cardName = bundle!!.getString(Constants.KEY_CARD_NAME)
//            companyName = bundle!!.getString(Constants.KEY_COMPANY_NAME)
//            isPhysical = bundle!!.getBoolean(Constants.KEY_IS_PHYSICAL, false)
//            isAlreadyVirtual = bundle!!.getBoolean(Constants.KEY_IS_ALREADY_VIRTUAL_VIRTUAL, false)
//            uuid = bundle!!.getString(Constants.KEY_UUID)
//            customerUuid = bundle!!.getString(Constants.KEY_CUSTOMER_UUID)
//        }
//
//        if (!ca.isB2cSubAccount(accountType)) {
//            setToolBarTitle(Translator.getInstance().getString(TraslatorKeys.setup_meeting_label_text_title))
//        } else {
//            setToolBarTitle(Translator.getInstance().getString(TraslatorKeys.setup_meeting_sub_account_label_text_title))
//        }
//
//
//        mapsClass = MapsUtil(activity, mapFragment)
//         geocoder = Geocoder(activity, Locale.getDefault())
//        inflater = getLayoutInflater()
//
//        if (ca.isBusinessAccount(locationSelectionViewModel.getAccountType())) {
//            initViewForBusiness()
//        } else if (ca.isConsumerAccount(locationSelectionViewModel.getAccountType())) {
//            initViewForConsumer()
//        } else {
//            initViewForSubCard()
//        }
//
//    }
//
//
//
//
////    fun setLocation() {
////         locationSelectionViewModel.getRecentCustomerAddressRequest(object : MyCallBack() {
////            fun onSuccess(responseDTO: ResponseDTO) {
////                hideProgressBar()
////                val userAddressResponseDTO = responseDTO as UserAddressResponseDTO
////                if (null != userAddressResponseDTO && null != userAddressResponseDTO!!.getData() && null != userAddressResponseDTO!!.getData().getAddress() && "" == userAddressResponseDTO!!.getData().getAddress()) {
////                    setRecentLocation(
////                        userAddressResponseDTO!!.getData().getLatitude(),
////                        userAddressResponseDTO!!.getData().getLongitude()
////                    )
////                } else {
////                    mapsClass.getMyLocation()
////                }
////            }
////
////            fun onFailure(resDTO: ResponseDTO) {
////                hideProgressBar()
////                if (null != resDTO.getErrors()) {
////                    try {
////                        for (i in 0 until resDTO.getErrors().size()) {
////                            showToast(resDTO.getErrors().get(i).getMessage())
////                        }
////                    } catch (e: Exception) {
////                        e.printStackTrace()
////                    }
////
////                }
////            }
////        })
////    }
//
//    private fun setRecentLocation(latitude: Double, longitude: Double) {
//        mapsClass.setMarker(latitude, longitude)
//    }
//
////    fun locationDialog() {
////        var formattedAddress = address
////        if ("" != etAddressTwo.text.toString()) {
////            formattedAddress = etAddressTwo.text.toString().trim { it <= ' ' } + ", " + address
////        }
////        val alertDialogBuilder: AlertDialog.Builder
////        alertDialogBuilder = AlertDialog.Builder(Objects.requireNonNull(activity))
////        alertDialogBuilder.setTitle(Translator.getInstance().getString(TraslatorKeys.meeting_confirmation_location_confirmation_title))
////        alertDialogBuilder.setMessage(formattedAddress)
////            .setCancelable(false)
////            .setPositiveButton(Translator.getInstance().getString(TraslatorKeys.meeting_confirmation_btn_location_confirmation_Positive)) { dialog, id ->
////                locationSelectionViewModel.setAddress(address)
////                locationSelectionViewModel.setAddressTwo(etAddressTwo.text.toString().trim { it <= ' ' })
////                locationSelectionViewModel.setLatitude(mapsClass.getLatitude())
////                locationSelectionViewModel.setLongitude(mapsClass.getLongitude())
////                if (ca.isBusinessAccount(locationSelectionViewModel.getAccountType())) {
////                    locationSelectionViewModel.navigate(DebitLocationSelectionViewModel(locationSelectionViewModel))
////                } else if (ca.isSupplementaryAccount(locationSelectionViewModel.getAccountType())) {
////                    if (isAlreadyVirtual) {
////                        locationSelectionViewModel.navigate(locationSelectionViewModel)
////                    } else {
////                        locationSelectionViewModel.navigate(SuppLocationViewModel(locationSelectionViewModel))
////                    }
////                } else {
////                    scheduleMeeting()
////                }
////            }
////            .setNegativeButton(
////                Translator.getInstance().getString(TraslatorKeys.meeting_confirmation_btn_location_confirmation_Negative),
////                { dialog, id -> dialog.cancel() })
////        alertDialog = alertDialogBuilder.create()
////        alertDialog.show()
////    }
//
//    fun autoPlacesFragment() {
//        try {
//            val typeFilter = AutocompleteFilter.Builder()
//                .setCountry("AE")
//                .build()
//            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter)
//                .build(Objects.requireNonNull(activity))
//            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
//        } catch (e: GooglePlayServicesRepairableException) {
//            // TODO: Handle the error.
//        } catch (e: GooglePlayServicesNotAvailableException) {
//            // TODO: Handle the error.
//        }
//
//    }
//
//
////    fun checkPermissions() {
////        permissionHelper = PermissionHelper(
////            this@LocationSelectionActivity,
////            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
////            100
////        )
////        permissionHelper.request(object : PermissionHelper.PermissionCallback() {
////            fun onPermissionGranted() {
////                setLocation()
////            }
////
////            fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
////                setLocation()
////            }
////
////            fun onPermissionDenied() {
////
////            }
////
////            fun onPermissionDeniedBySystem() {
////
////            }
////        })
////
////    }
//
////    fun onBackPressed() {
////        if (ca.isBusinessAccount(accountType) && !isSoleProprietary) {
////            locationSelectionViewModel.navigate(Navigation.UNREGISTER_EVENT)
////        }
////
////        super.onBackPressed()
////    }
//
////    fun getViewModel(): LocationSelectionViewModel {
////        return locationSelectionViewModel
////    }
//
//    companion object {
//
//
//        fun topLevelFocus(context: Context?) {
//            if (Activity::class.java.isAssignableFrom(context!!.javaClass)) {
//                val tlView = (context as Activity).window.decorView as ViewGroup
//                if (tlView != null) {
//                    tlView.isFocusable = true
//                    tlView.isFocusableInTouchMode = true
//                    tlView.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
//                }
//            }
//        }
//    }
}
