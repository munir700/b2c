package co.yap.modules.dashboard.cards.reportcard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.defaults.DefaultFragment
import kotlinx.android.synthetic.main.fragment_block_card_success.*

class BlockCardSuccessFragment : DefaultFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(
            R.layout.fragment_block_card_success,
            container, false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reOrderFeeValue = "50"
        tvFeeCaption.setText(
            reOrderFeeValue + " " +
                    Translator.getString(
                        context!!,
                        Strings.screen_card_blocked_display_text_note_android
                    )
        )


        btnReOrder.setOnClickListener {
            activity!!.onBackPressed()
        }

        tvAddLater.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}