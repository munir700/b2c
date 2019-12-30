package co.yap.modules.dashboard.cards.reportcard.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity.Companion.reportCardSuccess
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


        tvFeeCaption.text = "$reOrderFeeValue " +
                Translator.getString(
                    context!!,
                    Strings.screen_card_blocked_display_text_note_android
                )

        btnReOrder.setOnClickListener {
        // start reorder physical card flow from here
//            val action =
//                BlockCardSuccessFragmentDirections.actionBlockCardSuccessFragmentToAddSpareCardFragment(
//                    Translator.getString(
//                        requireContext(),
//                        screen_spare_card_landing_display_text_physical_card
//                    ),"","","","",true
//                )
//            findNavController().navigate(action)
            startActivity(Intent(context, ReorderCardActivity::class.java))
        }

        tvAddLater.setOnClickListener {
            reportCardSuccess = true
            setupActionsIntent()
             activity!!.finish()

        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardBlocked", true)
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }
}