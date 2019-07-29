package co.yap.modules.kyc.fragments

import android.os.Bundle
import co.yap.R
import co.yap.modules.kyc.fragments.LocationSelectionFragment
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.defaults.IDefault


class LocationBaseFragment : BaseBindingFragment<IDefault.ViewModel>(), IDefault.View {
    override val viewModel: IDefault.ViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = fragmentManager!!.findFragmentById(R.id.map) as LocationSelectionFragment
        /*mapFragment.initial_latitude = -10.0
        mapFragment.initial_longitude = 115.0
        mapFragment.initial_marker = "Inishol mawker"*/
        mapFragment.getMapAsync(mapFragment)

    }

}
