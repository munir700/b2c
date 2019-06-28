package co.yap.modules.onboarding.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import co.yap.R
import co.yap.widgets.CoreButton
import co.yap.widgets.CoreInputField
import kotlinx.android.synthetic.main.fragment_mobile.*

class MobileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_mobile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

//        view.findViewById<CoreButton>(R.id.next_button)
//            ?.enableButton(true)

        view.findViewById<Button>(R.id.next_button)?.setOnClickListener {
            inputMobileNumber?.setDrawableRightIcon(resources.getDrawable(R.drawable.invalid_name))

        }
    }
}