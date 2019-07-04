package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import co.yap.R
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.IBase

class AnimationTest1Fragment : BaseFragment<IBase.ViewModel<IBase.State>>() {
    override val viewModel: IBase.ViewModel<IBase.State>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val b = view?.findViewById<Button>(R.id.button11Next)
        b?.setOnClickListener{
            navigate(navFragmentId = R.id.animationTest2Fragment)
        }
    }
}