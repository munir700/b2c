package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentPhoneContactsBinding
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.ShareIntentListAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import android.net.Uri

class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_phone_contacts

    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initState()
        initComponents()
    }

    private fun initComponents() {
        getBinding().recycler.adapter = PhoneContactsAdaptor { viewModel.retry() }
        (getBinding().recycler.adapter as PhoneContactsAdaptor).setItemListener(listener)
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                getBinding().recycler.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
            } else {
                getBinding().txtError.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
                (getBinding().recycler.adapter as PhoneContactsAdaptor)?.setState(state)
                viewModel.parentViewModel?.yapContactLiveData?.postValue(viewModel.phoneContactLiveData.value?.filter { it.yapUser!! })
            }
        })
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.phoneContactLiveData.observe(this, Observer {
            (getBinding().recycler.adapter as PhoneContactsAdaptor).submitList(it)
            (getBinding().recycler.adapter as PhoneContactsAdaptor).setState(PagingState.DONE)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.userPackageType -> {

                }
                R.id.tvInvite -> {
                    sendInvite((data as Contact).mobileNo!!)
//                    Utils.shareText(requireContext(), getBody())
                }
                R.id.lyContact -> {
                    if (data is Contact && data.yapUser!!) {
                        if (parentFragment is YapToYapFragment) {
                            (parentFragment as YapToYapFragment).findNavController().navigate(
                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                    data.beneficiaryPictureUrl!!
                                    , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendInvite(phoneNo:String) {
//        val sendIntent = Intent(Intent.ACTION_SEND)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }
        val activities =
            context?.packageManager?.queryIntentActivities(sendIntent, 0) as List<ResolveInfo>
        val filteredPackages: ArrayList<ResolveInfo> = ArrayList()
        for (app: ResolveInfo in activities) {
            if (app.activityInfo.name.startsWith("com.whatsapp")) {
                filteredPackages.add(app)
            }else if(app.activityInfo.name.startsWith("com.google.android.gm")){
                filteredPackages.add(app)
            }
        }
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Share with...")
        val adapter = ShareIntentListAdaptor(
            context as Activity,
            R.layout.item_send_invite_contact,
            filteredPackages
        )

        builder.setAdapter(adapter) { dialog, which ->
            val info = adapter.getItem(which) as ResolveInfo
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setClassName(info.activityInfo.packageName, info.activityInfo.name)
            if(info.activityInfo.packageName.equals("com.whatsapp")){
                openWhatsappContact(phoneNo)
            }
            startActivity(intent)
        }
        builder.create()
        builder.show()

    }
    fun openWhatsappContact(number: String) {
        val uri = Uri.parse("smsto:$number")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.`package` = "com.whatsapp"
        i.type = "text/plain"
        startActivity(Intent.createChooser(i, ""))
    }
    private fun isPackageExisted(targetPackage: String): Boolean {
        val packages: List<ApplicationInfo> =
            context?.packageManager?.getInstalledApplications(0) as List<ApplicationInfo>

        for (packageInfo in packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true
        }
        return false
    }

    private fun getBody(): String {
        return "App LInk"
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    private fun getBinding(): FragmentPhoneContactsBinding {
        return (viewDataBinding as FragmentPhoneContactsBinding)
    }
}