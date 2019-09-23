package co.yap.modules.dashboard.cards.reportcard.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.viewmodels.ReportLostOrStolenCardViewModels
import co.yap.translation.Strings.screen_report_card_display_button_block_alert_cancel
import co.yap.translation.Strings.screen_report_card_display_button_block_alert_confirm
import co.yap.translation.Strings.screen_report_card_display_text_block_alert_message
import co.yap.translation.Strings.screen_report_card_display_text_block_alert_title
import co.yap.translation.Translator


class ReportLostOrStolenCardFragment :
    ReportOrLOstCardChildFragment<IRepostOrStolenCard.ViewModel>(), IRepostOrStolenCard.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_lost_or_stolen_card

    override val viewModel: IRepostOrStolenCard.ViewModel
        get() = ViewModelProviders.of(this).get(ReportLostOrStolenCardViewModels::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.llDamagedCard -> {
                    showDialog()


                }

                R.id.llStolenCard -> {


                }

            }
        })


    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(this.requireActivity())
        builder.setTitle(
            Translator.getString(
                context!!, screen_report_card_display_text_block_alert_title
            )
        )
        builder.setMessage(
            Translator.getString(
                context!!, screen_report_card_display_text_block_alert_message
            )
        )
        builder.setPositiveButton(
            Translator.getString(
                context!!, screen_report_card_display_button_block_alert_confirm
            )
        ) { dialog, which ->
            // call api
            showToast("ok")

        }

        builder.setNegativeButton(
            Translator.getString(
                context!!, screen_report_card_display_button_block_alert_cancel
            )
        ) { dialog, which ->
            //dismiss
            showToast("no")
        }


        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}