package co.yap.modules.dashboard.cards.reportcard.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity.Companion.reportCard
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity.Companion.reportCardSuccess
import co.yap.modules.dashboard.cards.reportcard.viewmodels.BlockCardSuccessViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.defaults.IDefault
import kotlinx.android.synthetic.main.fragment_block_card_success.*

class BlockCardSuccessFragment : BaseBindingFragment<IDefault.ViewModel>() {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.fragment_block_card_success

    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(BlockCardSuccessViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card: Card = reportCard
        val reOrderFeeValue =
            arguments?.let { BlockCardSuccessFragmentArgs.fromBundle(it).cardReorderFee } as String


        tvFeeCaption.text = "$reOrderFeeValue " +
                Translator.getString(
                    requireContext(),
                    Strings.screen_card_blocked_display_text_note_android
                )

        btnReOrder.setOnClickListener {
            startActivityForResult(
                ReorderCardActivity.newIntent(requireContext(), card),
                RequestCodes.REQUEST_REORDER_CARD
            )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_REORDER_CARD -> {
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)
                    cardReorder?.let {
                        if (it) {
                            setupActionsReorderIntent()
                            activity?.finish()
                        }
                    }
                }
            }
        }
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardBlocked", true)
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun setupActionsReorderIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardReorder", true)
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }
}