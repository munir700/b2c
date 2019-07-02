package co.yap.modules.onboarding.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_mobile.*


class MobileFragment : BaseBindingFragment<IMobile.ViewModel>() {

    override fun getBindingVariable(): Int = BR.mobileViewModel
    override fun getLayoutId(): Int = R.layout.fragment_mobile

    override val viewModel: IMobile.ViewModel
        get() = ViewModelProviders.of(this).get(MobileViewModel::class.java)

    override fun getString(resourceKey: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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


        inputMobileNumber?.editText!!.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                if (p0.toString().length == inputMobileNumber.PHONE_NUMBER_LENGTH) {
                    inputMobileNumber.settingUIForNormal()

                    var phoneNumber: String = p0.toString().trim()
                    phoneNumber = phoneNumber.trim().replace(" ", "")

                    checkMobileNumberValidation(phoneNumber)
                } else {
                    inputMobileNumber.settingUIForNormal()
                    next_button.enableButton(false)
                    inputMobileNumber?.setDrawableRightIcon(null)


                }

                if (p0.toString().length == 5) {
                    inputMobileNumber.editText.setCursorVisible(false)
                    /* disable backpress */
                    inputMobileNumber.cursorPlacement()
                    inputMobileNumber.editText.setOnKeyListener(object : View.OnKeyListener {

                        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                            if (keyCode == KeyEvent.KEYCODE_DEL) {
                                if (p1 <= 5) {
                                    return true
                                }
                            }
                            return false
                        }
                    })
                } else {
                    /*enable backpress*/

                    inputMobileNumber.cursorPlacement()
                    inputMobileNumber.editText.setOnKeyListener(object : View.OnKeyListener {
                        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                            if (keyCode == KeyEvent.KEYCODE_DEL) {
                                if (p1 <= 5) {
                                    return true
                                }
                            }
                            return false
                        }
                    })

                }
            }
        })

        view.findViewById<Button>(R.id.next_button)?.setOnClickListener {
            inputMobileNumber?.setDrawableRightIcon(resources.getDrawable(R.drawable.invalid_name))

        }

    }

    fun checkMobileNumberValidation(phoneNumber: String): Boolean? {
        if (!phoneNumber.trim().equals("")) {
            val input = phoneNumber.trim().replace("+", "")
            val regex = "[0-9]+"
            if (!input.matches(regex.toRegex())) {

                /* enable core button
                 set normal UI*/
                viewModel.state.valid = false
                viewModel.state.mobileError = getString(R.string.screen_phone_number_display_text_error)
                return false
            }
        }
        if (phoneNumber.trim().equals("")) {

            /* disable core button
             set error UI*/
            viewModel.state.valid = false
            viewModel.state.drawbleRight = null
            viewModel.state.mobileError = getString(R.string.screen_phone_number_display_text_error)
            return false
        }
        viewModel.state.valid = true
        viewModel.state.mobileError = ""
        viewModel.state.drawbleRight = context!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)

        return true
    }

}