package co.yap.sendmoney.helper

import android.graphics.PorterDuff
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.customers.responsedtos.beneficiary.BankParams
import co.yap.sendmoney.addbeneficiary.adaptor.AddBeneficiariesAdaptor
import co.yap.widgets.MaskTextWatcher
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.ThemeColorUtils

object SendMoneyBindingHelper {

    @BindingAdapter("adaptorListBankParams")
    @JvmStatic
    fun setAdaptorParam(recycleview: RecyclerView, list: ObservableField<List<BankParams>>) {
        if (!list.get().isNullOrEmpty())
            if (recycleview.adapter is AddBeneficiariesAdaptor)
                (recycleview.adapter as AddBeneficiariesAdaptor).setList(list.get() as List<BankParams>)
    }

    @JvmStatic
    @BindingAdapter("searchViewBG")
    fun searchViewBgColor(view: androidx.appcompat.widget.SearchView, search: Boolean?) {
        if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {
            view.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.bg_hh_disabled_search_view))
        }
    }

    @JvmStatic
    @BindingAdapter("setShapeColor")
    fun setShapeColor(view: ConstraintLayout, setHouseHoldShape: Boolean) {
        if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {
            if (setHouseHoldShape) {
                view.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.bg_hh_disabled_search_view))

            } else {
                view.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.bg_hh_search_view))

            }

        } else {
            view.setBackgroundDrawable(view.context.resources.getDrawable(R.drawable.bg_search_widget))

        }
    }

    @JvmStatic
    @BindingAdapter("searchViewBGColor")
    fun setsearchViewBGColor(view: FrameLayout, search: Boolean) {
        if (search) {

            if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                    .equals(Constants.THEME_HOUSEHOLD)
            ) {
                view.setBackgroundColor(ThemeColorUtils.colorSendMoneyToolBarAttribute(view.context))

            } else {
                view.setBackgroundColor(view.context.getColor(R.color.transparent))

            }
        } else {
            view.setBackgroundColor(view.context.getColor(R.color.transparent))

        }
    }


    @JvmStatic
    @BindingAdapter("setSVTextColor")
    fun setSVTextColor(view: TextView, isSearching: Boolean) {
        if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {
            if (isSearching) {
                view.setTextColor(ThemeColorUtils.colorSearchViewHintAttribute(view.context))

            } else {
                view.setTextColor(view.context.getColor(R.color.greyDark))

            }
        } else {
            view.setTextColor(ThemeColorUtils.colorSearchViewHintAttribute(view.context))

        }
    }

    @JvmStatic
    @BindingAdapter("setSVIconTint")
    fun setSVIconTint(view: ImageView, isSearching: Boolean) {
        if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {
            if (isSearching) {

                view.setColorFilter(
                    ThemeColorUtils.colorSearchViewHintAttribute(view.context),
                    PorterDuff.Mode.SRC_ATOP
                )

            } else {
                view.setColorFilter(
                    view.context.getColor(R.color.greyDark),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        } /*else {
            view.setColorFilter(view.context.getColor(R.color.greyDark), PorterDuff.Mode.SRC_ATOP)

        }*/
    }

    @JvmStatic
    @BindingAdapter("cashPayoutIconTint", "isSearching")
    fun setCashPayoutIconTint(view: ImageView, transferType: String, isSearching: Boolean) {
        if (SharedPreferenceManager.getInstance(view.context).getThemeValue()
                .equals(Constants.THEME_HOUSEHOLD)
        ) {
            if (transferType == SendMoneyBeneficiaryType.CASHPAYOUT.type && isSearching) {
                view.setColorFilter(
                    ThemeColorUtils.colorPrimaryDarkAttribute(view.context),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                view.setColorFilter(
                    view.context.getColor(R.color.greyDark),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        } else {
            view.setColorFilter(
                view.context.getColor(R.color.greyDark),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    @JvmStatic
    @BindingAdapter("ibanMask")
    fun maskIbanNo(view: AppCompatEditText, ibanMask: String?) {
        ibanMask?.let { view.addTextChangedListener(MaskTextWatcher(view, it)) }
    }

    @BindingAdapter("setBeneficiaryImageSrc")
    @JvmStatic
    fun setImageSrc(imageView: ImageView, transferType: String) {

        if (transferType == SendMoneyBeneficiaryType.CASHPAYOUT.type) {
            imageView.setImageResource(R.drawable.ic_cash)
        } else {
            imageView.setImageResource(R.drawable.ic_bank)
        }
    }
}