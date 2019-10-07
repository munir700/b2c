package co.yap.modules.dashboard.more.help.fragments

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHelpSupportBinding
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.help.adaptor.HelpSupportAdaptor
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.viewmodels.HelpSupportViewModel


class HelpSupportFragment : MoreBaseFragment<IHelpSupport.ViewModel>(), IHelpSupport.View {

    lateinit var adapter: HelpSupportAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_help_support

    override val viewModel: IHelpSupport.ViewModel
        get() = ViewModelProviders.of(this).get(HelpSupportViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initComponents()
    }

    private fun initComponents() {
        val content = SpannableString(viewModel.state.contactPhone.get())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        getBinding().tvCallPhone.text = content
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }


    private val observer = Observer<Int> {
        when (it) {
            R.id.lLyFaqs -> {
                openFaqsPage()
            }
            R.id.lyChat -> {
            }
            R.id.lyLiveWhatsApp -> {
            }
            R.id.lyCall -> {
                openDialer()
            }
            R.id.tbBtnBack -> {
                findNavController().navigateUp()
            }
        }
    }

    private fun openFaqsPage() {
        val url = "http://www.example.com"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun openDialer() {
        val intent = Intent(ACTION_DIAL)
        intent.data = Uri.parse("tel:" + viewModel.state.contactPhone.get())
        startActivity(intent)
    }

    override fun getBinding(): FragmentHelpSupportBinding {
        return viewDataBinding as FragmentHelpSupportBinding
    }
}