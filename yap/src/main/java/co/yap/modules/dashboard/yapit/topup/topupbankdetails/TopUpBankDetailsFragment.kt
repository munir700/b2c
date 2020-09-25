package co.yap.modules.dashboard.yapit.topup.topupbankdetails

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.managers.MyUserManager

class TopUpBankDetailsFragment : BaseBindingFragment<ITopUpBankDetails.ViewModel>(),
    ITopUpBankDetails.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_top_up_bank_details

    override val viewModel: ITopUpBankDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpBankDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnShare -> {
                shareInfo()
            }
        }
    }

    private fun shareInfo() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        // not set because ios team is not doing this.
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, viewModel.state.title.get())
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody())
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    private fun getBody(): String {
        return "Name: ${MyUserManager.user?.currentCustomer?.getFullName()}\n" +
                "IBAN: ${MyUserManager.user?.iban}\n" +
                "Swift/BIC: ${MyUserManager.user?.bank?.swiftCode}\n" +
                "Account: ${MyUserManager.user?.accountNo}\n" +
                "Bank: ${MyUserManager.user?.bank?.name}\n" +
                "Address: ${MyUserManager.user?.bank?.address}\n"
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.finish()
            }
        }
    }
}