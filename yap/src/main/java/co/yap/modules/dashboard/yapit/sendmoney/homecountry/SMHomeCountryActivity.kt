package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Activity
import android.content.Intent
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
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity
import co.yap.sendmoney.home.main.SMBeneficiaryParentActivity
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.ExtraKeys
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
        viewModel.getFxRates { response -> handleFxRateResponse(response) }
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

    override fun onResume() {
        super.onResume()
        viewModel.getHomeCountryRecentBeneficiaries()
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
                                viewModel.getHomeCountryRecentBeneficiaries()
                                viewModel.getFxRates { response -> handleFxRateResponse(response) }
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

    private fun getCountries(countries: ArrayList<Country>): List<Country> {
        val countriesWithoutUAE = countries.filter { it.isoCountryCode2Digit != "AE" }

        countriesWithoutUAE.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                context,
                it.isoCountryCode2Digit.toString()
            )
        }

        val position =
            countriesWithoutUAE.indexOf(countries.find { it.isoCountryCode2Digit == viewModel.homeCountry?.isoCountryCode2Digit })
        if (oldPosition == -1) {
            oldPosition = position
            countriesWithoutUAE[oldPosition].isSelected = true
        } else {
            countriesWithoutUAE[oldPosition].isSelected = false
            oldPosition = position
            countriesWithoutUAE[oldPosition].isSelected = true
        }

        return countriesWithoutUAE
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Beneficiary) {
                startMoneyTransfer(data, pos)
            }
        }
    }

    private fun startSendMoneyFlow() {
        launchActivity<SMBeneficiaryParentActivity>(requestCode = RequestCodes.REQUEST_TRANSFER_MONEY) {
            putExtra(
                ExtraKeys.SEND_MONEY_TYPE.name,
                SendMoneyTransferType.HOME_COUNTRY.name
            )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_TRANSFER_MONEY -> {
                    if (data?.getBooleanExtra(Constants.MONEY_TRANSFERED, false) == true) {
                        setResultData()
                    }
                }
            }
        }
    }

    private fun setResultData() {
        val intent = Intent()
        intent.putExtra(Constants.MONEY_TRANSFERED, true)
        setResult(Activity.RESULT_OK, intent)
        finish()
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

    private fun handleFxRateResponse(it: FxRateResponse.Data?) {
        it?.let { fxRate ->
            viewModel.state.rate?.set("${fxRate.fxRates?.get(0)?.rate}")
            viewModel.state.homeCountryCurrency?.set(fxRate.toCurrencyCode)
            viewModel.state.time?.set(
                DateUtils.reformatLiveStringDate(
                    fxRate.date.toString(),
                    inputFormatter = DateUtils.SERVER_DATE_FORMAT,
                    outFormatter = DateUtils.FXRATE_DATE_TIME_FORMAT
                )
            )
        }
    }
}