package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.databinding.ActivitySmHomeCountryBinding
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.getBeneficiaryTransferType
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import java.util.*


class SMHomeCountryActivity : BaseBindingActivity<ISMHomeCountry.ViewModel>(), ISMHomeCountry.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_sm_home_country
    override val viewModel: ISMHomeCountry.ViewModel
        get() = ViewModelProviders.of(this).get(SMHomeCountryViewModel::class.java)
    var oldPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        setupRecycler()
    }

    private fun setupRecycler() {
        viewModel.recentsAdapter.allowFullItemClickListener = true
        viewModel.recentsAdapter.setItemListener(itemClickListener)
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnSendMoney -> {
                    startSendMoneyFlow()
                }
                R.id.tvChangeHomeCountry -> {
                    setupCountriesList()
                }
                R.id.tvHideRecents, R.id.recents -> {
                    viewModel.state.isRecentsVisible.set(getBinding().recyclerViewRecents.visibility == View.VISIBLE)
                    getBinding().recyclerViewRecents.visibility =
                        if (getBinding().recyclerViewRecents.visibility == View.GONE) View.VISIBLE else View.GONE
                }
            }
        })
    }

    private fun setupCountriesList() {
        val countries: ArrayList<Country> = SessionManager.getCountries()
        this.supportFragmentManager.let {
            val coreBottomSheet = CoreBottomSheet(
                object :
                    OnItemClickListener {
                    override fun onItemClick(view: View, data: Any, pos: Int) {
                        if (viewModel.homeCountry != (data as Country)) {
                            viewModel.homeCountry = data
                            viewModel.updateHomeCountry {
                                SessionManager.getAccountInfo()
                                viewModel.populateData(data)
                            }
                        }
                    }
                },
                bottomSheetItems = getCountries(countries).toMutableList(),
                headingLabel = "Change home country",
                viewType = Constants.VIEW_WITH_FLAG
            )
            coreBottomSheet.show(it, "")
        }
    }

    private fun getCountries(countries: ArrayList<Country>): ArrayList<Country> {
        countries.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                context,
                it.isoCountryCode2Digit.toString()
            )
        }

        val position =
            countries.indexOf(countries.find { it.isoCountryCode2Digit == viewModel.homeCountry?.isoCountryCode2Digit })
        if (oldPosition == -1) {
            oldPosition = position
            countries[oldPosition].isSelected = true
        } else {
            countries[oldPosition].isSelected = false
            oldPosition = position
            countries[oldPosition].isSelected = true
        }

        return countries
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Beneficiary) {
                startMoneyTransfer(data, pos)
            }
        }
    }

    private fun startSendMoneyFlow() {
        launchActivity<SendMoneyLandingActivity> {
            putExtra(
                SendMoneyLandingActivity.TransferType,
                SendMoneyTransferType.HOME_COUNTRY.name
            )
            putExtra(SendMoneyLandingActivity.searching, false)
        }
    }

    private fun startMoneyTransfer(beneficiary: Beneficiary?, position: Int) {
        launchActivity<BeneficiaryFundTransferActivity>(
            requestCode = RequestCodes.REQUEST_TRANSFER_MONEY,
            type = beneficiary.getBeneficiaryTransferType()
        ) {
            putExtra(Constants.BENEFICIARY, beneficiary)
            putExtra(Constants.POSITION, position)
            putExtra(Constants.IS_NEW_BENEFICIARY, false)
        }
    }

    private fun getBinding(): ActivitySmHomeCountryBinding =
        (viewDataBinding as ActivitySmHomeCountryBinding)

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}