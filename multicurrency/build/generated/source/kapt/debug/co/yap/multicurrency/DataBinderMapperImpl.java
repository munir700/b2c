package co.yap.multicurrency;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import co.yap.multicurrency.databinding.ActivityMcLandingBindingImpl;
import co.yap.multicurrency.databinding.ActivityRatesAlertsBindingImpl;
import co.yap.multicurrency.databinding.FragmentCurrencyExchangeConfirmationBindingImpl;
import co.yap.multicurrency.databinding.FragmentCurrencyExchangeInputBindingImpl;
import co.yap.multicurrency.databinding.FragmentCurrencyExchangeSuccessBindingImpl;
import co.yap.multicurrency.databinding.FragmentCurrencyPickerBindingImpl;
import co.yap.multicurrency.databinding.FragmentMcAddPriceAlertBindingImpl;
import co.yap.multicurrency.databinding.FragmentMcAlertsBindingImpl;
import co.yap.multicurrency.databinding.FragmentMcRatesBindingImpl;
import co.yap.multicurrency.databinding.FragmentMcRatesDetailsBindingImpl;
import co.yap.multicurrency.databinding.FragmentRatesAndAlertsBindingImpl;
import co.yap.multicurrency.databinding.FragmentWalletBindingImpl;
import co.yap.multicurrency.databinding.FragmentWalletDetailsBindingImpl;
import co.yap.multicurrency.databinding.FragmentWalletV2BindingImpl;
import co.yap.multicurrency.databinding.ItemCurrencyCountryBindingImpl;
import co.yap.multicurrency.databinding.ItemWalletBindingImpl;
import co.yap.multicurrency.databinding.ItemWalletDetailListBindingImpl;
import co.yap.multicurrency.databinding.ItemWalletV2BindingImpl;
import co.yap.multicurrency.databinding.LayoutDefaultWalletBankDetailsBindingImpl;
import co.yap.multicurrency.databinding.LayoutItemMcAlertBindingImpl;
import co.yap.multicurrency.databinding.LayoutItemMcRateBindingImpl;
import co.yap.multicurrency.databinding.LayoutOtherCurrencyWalletActivationBindingImpl;
import co.yap.multicurrency.databinding.LayoutRatesAndAlertsToolbarBindingImpl;
import co.yap.multicurrency.databinding.LayoutWalletActionsBindingImpl;
import co.yap.multicurrency.databinding.LayoutWalletsToolbarBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMCLANDING = 1;

  private static final int LAYOUT_ACTIVITYRATESALERTS = 2;

  private static final int LAYOUT_FRAGMENTCURRENCYEXCHANGECONFIRMATION = 3;

  private static final int LAYOUT_FRAGMENTCURRENCYEXCHANGEINPUT = 4;

  private static final int LAYOUT_FRAGMENTCURRENCYEXCHANGESUCCESS = 5;

  private static final int LAYOUT_FRAGMENTCURRENCYPICKER = 6;

  private static final int LAYOUT_FRAGMENTMCADDPRICEALERT = 7;

  private static final int LAYOUT_FRAGMENTMCALERTS = 8;

  private static final int LAYOUT_FRAGMENTMCRATES = 9;

  private static final int LAYOUT_FRAGMENTMCRATESDETAILS = 10;

  private static final int LAYOUT_FRAGMENTRATESANDALERTS = 11;

  private static final int LAYOUT_FRAGMENTWALLET = 12;

  private static final int LAYOUT_FRAGMENTWALLETDETAILS = 13;

  private static final int LAYOUT_FRAGMENTWALLETV2 = 14;

  private static final int LAYOUT_ITEMCURRENCYCOUNTRY = 15;

  private static final int LAYOUT_ITEMWALLET = 16;

  private static final int LAYOUT_ITEMWALLETDETAILLIST = 17;

  private static final int LAYOUT_ITEMWALLETV2 = 18;

  private static final int LAYOUT_LAYOUTDEFAULTWALLETBANKDETAILS = 19;

  private static final int LAYOUT_LAYOUTITEMMCALERT = 20;

  private static final int LAYOUT_LAYOUTITEMMCRATE = 21;

  private static final int LAYOUT_LAYOUTOTHERCURRENCYWALLETACTIVATION = 22;

  private static final int LAYOUT_LAYOUTRATESANDALERTSTOOLBAR = 23;

  private static final int LAYOUT_LAYOUTWALLETACTIONS = 24;

  private static final int LAYOUT_LAYOUTWALLETSTOOLBAR = 25;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(25);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.activity_mc_landing, LAYOUT_ACTIVITYMCLANDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.activity_rates_alerts, LAYOUT_ACTIVITYRATESALERTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_currency_exchange_confirmation, LAYOUT_FRAGMENTCURRENCYEXCHANGECONFIRMATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_currency_exchange_input, LAYOUT_FRAGMENTCURRENCYEXCHANGEINPUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_currency_exchange_success, LAYOUT_FRAGMENTCURRENCYEXCHANGESUCCESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_currency_picker, LAYOUT_FRAGMENTCURRENCYPICKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_mc_add_price_alert, LAYOUT_FRAGMENTMCADDPRICEALERT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_mc_alerts, LAYOUT_FRAGMENTMCALERTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_mc_rates, LAYOUT_FRAGMENTMCRATES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_mc_rates_details, LAYOUT_FRAGMENTMCRATESDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_rates_and_alerts, LAYOUT_FRAGMENTRATESANDALERTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_wallet, LAYOUT_FRAGMENTWALLET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_wallet_details, LAYOUT_FRAGMENTWALLETDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.fragment_wallet_v2, LAYOUT_FRAGMENTWALLETV2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.item_currency_country, LAYOUT_ITEMCURRENCYCOUNTRY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.item_wallet, LAYOUT_ITEMWALLET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.item_wallet_detail_list, LAYOUT_ITEMWALLETDETAILLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.item_wallet_v2, LAYOUT_ITEMWALLETV2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_default_wallet_bank_details, LAYOUT_LAYOUTDEFAULTWALLETBANKDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_item_mc_alert, LAYOUT_LAYOUTITEMMCALERT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_item_mc_rate, LAYOUT_LAYOUTITEMMCRATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_other_currency_wallet_activation, LAYOUT_LAYOUTOTHERCURRENCYWALLETACTIVATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_rates_and_alerts_toolbar, LAYOUT_LAYOUTRATESANDALERTSTOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_wallet_actions, LAYOUT_LAYOUTWALLETACTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.multicurrency.R.layout.layout_wallets_toolbar, LAYOUT_LAYOUTWALLETSTOOLBAR);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMCLANDING: {
          if ("layout/activity_mc_landing_0".equals(tag)) {
            return new ActivityMcLandingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mc_landing is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYRATESALERTS: {
          if ("layout/activity_rates_alerts_0".equals(tag)) {
            return new ActivityRatesAlertsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_rates_alerts is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCURRENCYEXCHANGECONFIRMATION: {
          if ("layout/fragment_currency_exchange_confirmation_0".equals(tag)) {
            return new FragmentCurrencyExchangeConfirmationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_currency_exchange_confirmation is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCURRENCYEXCHANGEINPUT: {
          if ("layout/fragment_currency_exchange_input_0".equals(tag)) {
            return new FragmentCurrencyExchangeInputBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_currency_exchange_input is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCURRENCYEXCHANGESUCCESS: {
          if ("layout/fragment_currency_exchange_success_0".equals(tag)) {
            return new FragmentCurrencyExchangeSuccessBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_currency_exchange_success is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCURRENCYPICKER: {
          if ("layout/fragment_currency_picker_0".equals(tag)) {
            return new FragmentCurrencyPickerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_currency_picker is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMCADDPRICEALERT: {
          if ("layout/fragment_mc_add_price_alert_0".equals(tag)) {
            return new FragmentMcAddPriceAlertBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_mc_add_price_alert is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMCALERTS: {
          if ("layout/fragment_mc_alerts_0".equals(tag)) {
            return new FragmentMcAlertsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_mc_alerts is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMCRATES: {
          if ("layout/fragment_mc_rates_0".equals(tag)) {
            return new FragmentMcRatesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_mc_rates is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMCRATESDETAILS: {
          if ("layout/fragment_mc_rates_details_0".equals(tag)) {
            return new FragmentMcRatesDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_mc_rates_details is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTRATESANDALERTS: {
          if ("layout/fragment_rates_and_alerts_0".equals(tag)) {
            return new FragmentRatesAndAlertsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_rates_and_alerts is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWALLET: {
          if ("layout/fragment_wallet_0".equals(tag)) {
            return new FragmentWalletBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_wallet is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWALLETDETAILS: {
          if ("layout/fragment_wallet_details_0".equals(tag)) {
            return new FragmentWalletDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_wallet_details is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWALLETV2: {
          if ("layout/fragment_wallet_v2_0".equals(tag)) {
            return new FragmentWalletV2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_wallet_v2 is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMCURRENCYCOUNTRY: {
          if ("layout/item_currency_country_0".equals(tag)) {
            return new ItemCurrencyCountryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_currency_country is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMWALLET: {
          if ("layout/item_wallet_0".equals(tag)) {
            return new ItemWalletBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_wallet is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMWALLETDETAILLIST: {
          if ("layout/item_wallet_detail_list_0".equals(tag)) {
            return new ItemWalletDetailListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_wallet_detail_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMWALLETV2: {
          if ("layout/item_wallet_v2_0".equals(tag)) {
            return new ItemWalletV2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_wallet_v2 is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTDEFAULTWALLETBANKDETAILS: {
          if ("layout/layout_default_wallet_bank_details_0".equals(tag)) {
            return new LayoutDefaultWalletBankDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_default_wallet_bank_details is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTITEMMCALERT: {
          if ("layout/layout_item_mc_alert_0".equals(tag)) {
            return new LayoutItemMcAlertBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_item_mc_alert is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTITEMMCRATE: {
          if ("layout/layout_item_mc_rate_0".equals(tag)) {
            return new LayoutItemMcRateBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_item_mc_rate is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTOTHERCURRENCYWALLETACTIVATION: {
          if ("layout/layout_other_currency_wallet_activation_0".equals(tag)) {
            return new LayoutOtherCurrencyWalletActivationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_other_currency_wallet_activation is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTRATESANDALERTSTOOLBAR: {
          if ("layout/layout_rates_and_alerts_toolbar_0".equals(tag)) {
            return new LayoutRatesAndAlertsToolbarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_rates_and_alerts_toolbar is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTWALLETACTIONS: {
          if ("layout/layout_wallet_actions_0".equals(tag)) {
            return new LayoutWalletActionsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_wallet_actions is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTWALLETSTOOLBAR: {
          if ("layout/layout_wallets_toolbar_0".equals(tag)) {
            return new LayoutWalletsToolbarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_wallets_toolbar is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(4);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new co.yap.networking.DataBinderMapperImpl());
    result.add(new co.yap.translation.DataBinderMapperImpl());
    result.add(new co.yap.yapcore.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(40);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "amount");
      sKeys.put(2, "beneficiaryCountry");
      sKeys.put(3, "buttonTitle");
      sKeys.put(4, "cardSerialNumber");
      sKeys.put(5, "cityOfBirth");
      sKeys.put(6, "color");
      sKeys.put(7, "confirmPin");
      sKeys.put(8, "currencyType");
      sKeys.put(9, "dialerError");
      sKeys.put(10, "error");
      sKeys.put(11, "errorMessage");
      sKeys.put(12, "flowType");
      sKeys.put(13, "forgotTextVisibility");
      sKeys.put(14, "frameActivityViewModel");
      sKeys.put(15, "imageUrl");
      sKeys.put(16, "loading");
      sKeys.put(17, "newPin");
      sKeys.put(18, "oldPin");
      sKeys.put(19, "otp");
      sKeys.put(20, "otpDataModel");
      sKeys.put(21, "passCode");
      sKeys.put(22, "pincode");
      sKeys.put(23, "selectedCountry");
      sKeys.put(24, "sequence");
      sKeys.put(25, "similar");
      sKeys.put(26, "subTitle");
      sKeys.put(27, "timer");
      sKeys.put(28, "title");
      sKeys.put(29, "titleSetPin");
      sKeys.put(30, "toast");
      sKeys.put(31, "toolbarTitle");
      sKeys.put(32, "toolbarVisibility");
      sKeys.put(33, "valid");
      sKeys.put(34, "validResend");
      sKeys.put(35, "verificationDescription");
      sKeys.put(36, "verificationDescriptionForLogo");
      sKeys.put(37, "verificationTitle");
      sKeys.put(38, "viewModel");
      sKeys.put(39, "webViewFragmentViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(25);

    static {
      sKeys.put("layout/activity_mc_landing_0", co.yap.multicurrency.R.layout.activity_mc_landing);
      sKeys.put("layout/activity_rates_alerts_0", co.yap.multicurrency.R.layout.activity_rates_alerts);
      sKeys.put("layout/fragment_currency_exchange_confirmation_0", co.yap.multicurrency.R.layout.fragment_currency_exchange_confirmation);
      sKeys.put("layout/fragment_currency_exchange_input_0", co.yap.multicurrency.R.layout.fragment_currency_exchange_input);
      sKeys.put("layout/fragment_currency_exchange_success_0", co.yap.multicurrency.R.layout.fragment_currency_exchange_success);
      sKeys.put("layout/fragment_currency_picker_0", co.yap.multicurrency.R.layout.fragment_currency_picker);
      sKeys.put("layout/fragment_mc_add_price_alert_0", co.yap.multicurrency.R.layout.fragment_mc_add_price_alert);
      sKeys.put("layout/fragment_mc_alerts_0", co.yap.multicurrency.R.layout.fragment_mc_alerts);
      sKeys.put("layout/fragment_mc_rates_0", co.yap.multicurrency.R.layout.fragment_mc_rates);
      sKeys.put("layout/fragment_mc_rates_details_0", co.yap.multicurrency.R.layout.fragment_mc_rates_details);
      sKeys.put("layout/fragment_rates_and_alerts_0", co.yap.multicurrency.R.layout.fragment_rates_and_alerts);
      sKeys.put("layout/fragment_wallet_0", co.yap.multicurrency.R.layout.fragment_wallet);
      sKeys.put("layout/fragment_wallet_details_0", co.yap.multicurrency.R.layout.fragment_wallet_details);
      sKeys.put("layout/fragment_wallet_v2_0", co.yap.multicurrency.R.layout.fragment_wallet_v2);
      sKeys.put("layout/item_currency_country_0", co.yap.multicurrency.R.layout.item_currency_country);
      sKeys.put("layout/item_wallet_0", co.yap.multicurrency.R.layout.item_wallet);
      sKeys.put("layout/item_wallet_detail_list_0", co.yap.multicurrency.R.layout.item_wallet_detail_list);
      sKeys.put("layout/item_wallet_v2_0", co.yap.multicurrency.R.layout.item_wallet_v2);
      sKeys.put("layout/layout_default_wallet_bank_details_0", co.yap.multicurrency.R.layout.layout_default_wallet_bank_details);
      sKeys.put("layout/layout_item_mc_alert_0", co.yap.multicurrency.R.layout.layout_item_mc_alert);
      sKeys.put("layout/layout_item_mc_rate_0", co.yap.multicurrency.R.layout.layout_item_mc_rate);
      sKeys.put("layout/layout_other_currency_wallet_activation_0", co.yap.multicurrency.R.layout.layout_other_currency_wallet_activation);
      sKeys.put("layout/layout_rates_and_alerts_toolbar_0", co.yap.multicurrency.R.layout.layout_rates_and_alerts_toolbar);
      sKeys.put("layout/layout_wallet_actions_0", co.yap.multicurrency.R.layout.layout_wallet_actions);
      sKeys.put("layout/layout_wallets_toolbar_0", co.yap.multicurrency.R.layout.layout_wallets_toolbar);
    }
  }
}
