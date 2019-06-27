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
import co.yap.widgets.CoreInputField

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
        view.findViewById<CoreInputField>(R.id.inputMobileNumber)
            ?.settingUIForError(getString(R.string.screen_phone_number_display_text_error))

        view.findViewById<Button>(R.id.next_button)?.setOnClickListener {
            //            findNavController().navigate(R.id.action_mobileFragment_to_emailFragment, null, options)
            view.findViewById<CoreInputField>(R.id.inputMobileNumber)?.settingUIForNormal()

        }
    }
}