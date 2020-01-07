package co.yap.modules.location.activities

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import co.yap.R
import co.yap.modules.location.helper.MapSupportActivity
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.networking.cards.responsedtos.Address
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_address_selection.*
import kotlinx.android.synthetic.main.layout_maps.*

class LocationSelectionActivity : MapSupportActivity(), ILocationSelection.View {

    companion object {
        const val ADDRESS = "address"
        private const val HEADING = "heading"
        private const val SUB_HEADING = "subHeading"
        fun newIntent(
            context: Context,
            address: Address? = null,
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
        initMapFragment()
        settAddressFromIntent()
        updateHeadings()
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
            if (intent.hasExtra(HEADING)) {
                val subHeading = intent.getStringExtra(SUB_HEADING)
                viewModel.state.subHeadingTitle.set(subHeading)
            }
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.isTermsChecked.addOnPropertyChangedCallback(stateObserver)

    }

    private fun initMapFragment() {
//        displayLocationSettingsRequest(requireContext())
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
                setIntentAction()
            }

            R.id.btnLocation -> {
                expandMap()
            }

            R.id.ivClose -> {
                settAddressFromIntent()
                if (viewModel.state.isShowLocationCard.get() == true)
                    startAnimateLocationCard()
                else {
                    collapseMap()
                }
            }

            R.id.btnConfirm -> {
                startAnimateLocationCard()
            }
        }
    }


    private fun expandMap() {
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


    private fun setIntentAction() {
        val intent = Intent()
        intent.putExtra(ADDRESS, viewModel.address)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}