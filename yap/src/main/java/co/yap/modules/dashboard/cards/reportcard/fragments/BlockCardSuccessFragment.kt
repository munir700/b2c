package co.yap.modules.dashboard.cards.reportcard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.reportcard.viewmodels.BlockCardSuccessViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.defaults.IDefault
import kotlinx.android.synthetic.main.fragment_block_card_success.*

class BlockCardSuccessFragment : BaseBindingFragment<IDefault.ViewModel>() {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.fragment_block_card_success

    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(BlockCardSuccessViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reOrderFeeValue =
            arguments?.let { BlockCardSuccessFragmentArgs.fromBundle(it).cardReorderFee } as String


        tvFeeCaption.setText(
            reOrderFeeValue + " " +
                    Translator.getString(
                        context!!,
                        Strings.screen_card_blocked_display_text_note_android
                    )
        )

        btnReOrder.setOnClickListener {
            //        start reorder physical card flow from here
            findNavController().navigate(R.id.action_blockCardSuccessFragment_to_addSpareCardFragment)
        }

        tvAddLater.setOnClickListener {
            activity!!.onBackPressed()
            //        finish and go back to detail screen from here

        }
    }
}