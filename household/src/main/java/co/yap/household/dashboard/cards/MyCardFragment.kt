package co.yap.household.dashboard.cards

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentMyCardBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.carddetaildialog.CardDetailsDialogPagerAdapter
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.carddetaildialog.CardDetailsModel
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.DividerItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.showSnackBar
import co.yap.yapcore.helpers.spannables.underline
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option
import com.google.android.material.snackbar.Snackbar
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class MyCardFragment :
    BaseRecyclerViewFragment<FragmentMyCardBinding, IMyCard.State, MyCardVM, MyCardFragment.Adapter, Transaction>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_my_card

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setupToolbar(mViewDataBinding.toolbar, R.menu.menu_options)
        setHasOptionsMenu(true)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                R.drawable.item_divider,
                showFirstDivider = false,
                showLastDivider = false,
                marginStart = dimen(co.yap.R.dimen._70sdp)
            )
        )
        viewModel.getPrimaryCard()
        checkFreezeUnfreezStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun toolBarVisibility() = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showBottomSheet()
        return super.onOptionsItemSelected(item)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            viewModel.EVENT_FREEZE_UNFREEZE_CARD -> {
                checkFreezeUnfreezStatus()
            }
            R.id.btnCardDetails -> {
                viewModel.getCardDetails()
            }
            viewModel.EVENT_CARD_DETAILS -> {
                showCardDetailsPopup()
            }
        }

    }

    private fun showCardDetailsPopup() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_card_details)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnClose = dialog.findViewById(R.id.ivCross) as ImageView
        var cardType = ""
        var cardNumber: String? = ""
        if (null != viewModel.cardDetail.cardNumber) {
            if (viewModel.cardDetail.cardNumber?.trim()?.contains(" ") == true) {
                cardNumber = viewModel.cardDetail.cardNumber
            } else {
                if (viewModel.cardDetail.cardNumber?.length == 16) {
                    val formattedCardNumber: StringBuilder =
                        StringBuilder(viewModel.cardDetail.cardNumber ?: "")
                    formattedCardNumber.insert(4, " ")
                    formattedCardNumber.insert(9, " ")
                    formattedCardNumber.insert(14, " ")
                    cardNumber = formattedCardNumber.toString()
                }
            }
        }

        if (Constants.CARD_TYPE_DEBIT == viewModel.card?.cardType) {
            cardType = "Primary card"
        } else {
            cardType = if (viewModel.card?.nameUpdated == true) {
                viewModel.card?.cardName ?: ""
            } else {
                if (viewModel.card?.physical == true) {
                    Constants.TEXT_SPARE_CARD_PHYSICAL
                } else {
                    Constants.TEXT_SPARE_CARD_VIRTUAL
                }
            }
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        val indicator = dialog.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val viewPager = dialog.findViewById<ViewPager2>(R.id.cardsPager)
        val pagerList = mutableListOf<CardDetailsModel>()
        pagerList.add(
            CardDetailsModel(
                cardExpiry = viewModel.cardDetail.expiryDate,
                cardType = cardType,
                cardNumber = cardNumber, cardCvv = viewModel.cardDetail.cvv
            )
        )
        pagerList.add(
            CardDetailsModel(
                cardExpiry = viewModel.cardDetail.expiryDate,
                cardType = cardType,
                cardNumber = cardNumber, cardCvv = viewModel.cardDetail.cvv
            )
        )
        val cardDetailsPagerAdapter = CardDetailsDialogPagerAdapter(pagerList)
        viewPager?.adapter = cardDetailsPagerAdapter
        indicator?.setViewPager2(viewPager)
        dialog.show()
    }

    private fun showBottomSheet() {
        showActionPickerBottomSheet(
            options = ArrayList<Option>().apply {
                add(
                    Option().setId(R.id.change_pin.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_change_pin_text))
                )
                add(
                    Option().setId(R.id.freeze_card.toLong())
                        .setTitle(viewModel.state.cardStatus.value.toString())
                )
                add(
                    Option().setId(R.id.view_statement.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_view_statement_text))
                )
                add(
                    Option().setId(R.id.report_lost_card.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_report_lost_card_text))
                )
                add(
                    Option().setId(R.id.cancel.toLong())
                        .setTitle(getString(Strings.screen_household_my_card_screen_menu_cancel_text))
                )
            },
            config = actionPickerConfig() {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(co.yap.R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    R.id.change_pin.toLong() -> startActivity(
                        ChangeCardPinActivity.newIntent(
                            requireContext(),
                            viewModel.card?.cardSerialNumber ?: ""
                        )
                    )
                    R.id.freeze_card.toLong() -> viewModel.freezeUnfreezeCard()
                    R.id.view_statement.toLong() -> {
                        launchActivity<CardStatementsActivity> {
                            putExtra("card", viewModel.card)
                            putExtra("isFromDrawer", false)
                        }
                    }
                    R.id.report_lost_card.toLong() -> {
                        startActivityForResult(
                            ReportLostOrStolenCardActivity.newIntent(
                                requireContext(),
                                viewModel.card!!
                            ), Constants.REQUEST_REPORT_LOST_OR_STOLEN
                        )
                    }
                    R.id.cancel.toLong() -> toast("Cancel")
                }
            }
        )
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white

    class Adapter(mValue: MutableList<Transaction>, navigation: NavController?) :
        BaseRVAdapter<Transaction, MyCardRecentTransactionsItemVM, BaseViewHolder<Transaction, MyCardRecentTransactionsItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: MyCardRecentTransactionsItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = MyCardRecentTransactionsItemVM()
        override fun getVariableId() = BR.viewModel
    }

    private fun checkFreezeUnfreezStatus() {
        viewModel.card?.blocked?.let {
            if (it) {
                showSnackBar(
                    msg = getString(Strings.screen_cards_display_text_freeze_card),
                    viewBgColor = R.color.colorPrimary,
                    colorOfMessage = R.color.white,
                    gravity = Gravity.TOP,
                    marginTop = requireContext().dimen(R.dimen.toolbar_height),
                    duration = Snackbar.LENGTH_INDEFINITE,
                    actionText = underline(getString(Strings.screen_cards_display_text_freeze_card_action)),
                    clickListener = View.OnClickListener { viewModel.freezeUnfreezeCard() }
                )
                viewModel.state.cardStatus.value = "Unfreeze card"
            } else {
                cancelAllSnackBar()
                viewModel.state.cardStatus.value = "Freeze card"
            }
        }
    }
}
