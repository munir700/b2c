package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import androidx.appcompat.app.AlertDialog
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.states.ReportOrStolenCardState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ReportLostOrStolenCardViewModels(application: Application) :
    ReportLostOrStolenCardChildViewModels<IRepostOrStolenCard.State>(application),
    IRepostOrStolenCard.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportOrStolenCardState = ReportOrStolenCardState()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))

    }

    override fun handlePressOnDamagedCard(id: Int) {
        clickEvent.setValue(id)

//        showDialog()
    }

    override fun handlePressOnLostOrStolen(id: Int) {
        clickEvent.setValue(id)

    }

    override fun handlePressOnReportAndBlockButton(id: Int) {
        clickEvent.setValue(id)
    }


    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle("App background color")

        // Display a message on alert dialog
        builder.setMessage("Are you want to set the app background color to RED?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES") { dialog, which ->
            // Do something when user press the positive button
//            Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()

            // Change the app background color
//            root_layout.setBackgroundColor(Color.RED)
        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No") { dialog, which ->
            //            Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
        }


        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel") { _, _ ->
            //            Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }


}