package co.yap.sendmoney;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import co.yap.sendmoney.databinding.ActivityBeneficiaryCashTransferBindingImpl;
import co.yap.sendmoney.databinding.ActivityEditBeneficiaryBindingImpl;
import co.yap.sendmoney.databinding.ActivitySendMoneyHomeBindingImpl;
import co.yap.sendmoney.databinding.ActivitySendMoneyLandingBindingImpl;
import co.yap.sendmoney.databinding.BottomSheetInviteFriendBindingImpl;
import co.yap.sendmoney.databinding.BottomSheetPopBindingImpl;
import co.yap.sendmoney.databinding.FragmentAddBankDetailBindingImpl;
import co.yap.sendmoney.databinding.FragmentAddBeneficiaryCashFlowBindingImpl;
import co.yap.sendmoney.databinding.FragmentAddBeneficiaryDomesticTransferBindingImpl;
import co.yap.sendmoney.databinding.FragmentAddBeneficiaryInternationalBankTransferBindingImpl;
import co.yap.sendmoney.databinding.FragmentBeneficiaryAccountDetailBindingImpl;
import co.yap.sendmoney.databinding.FragmentBeneficiaryOverviewBindingImpl;
import co.yap.sendmoney.databinding.FragmentCashTransferBindingImpl;
import co.yap.sendmoney.databinding.FragmentCashTransferConfirmationBindingImpl;
import co.yap.sendmoney.databinding.FragmentInternationalFundsTransferBindingImpl;
import co.yap.sendmoney.databinding.FragmentInternationalTransactionConfirmationBindingImpl;
import co.yap.sendmoney.databinding.FragmentSelectCountryBindingImpl;
import co.yap.sendmoney.databinding.FragmentTransferSuccessBindingImpl;
import co.yap.sendmoney.databinding.FragmentTransferTypeBindingImpl;
import co.yap.sendmoney.databinding.ItemBankParamsBindingImpl;
import co.yap.sendmoney.databinding.ItemBeneficiariesBindingImpl;
import co.yap.sendmoney.databinding.ItemRakBankBindingImpl;
import co.yap.sendmoney.databinding.ItemReasonListBindingImpl;
import co.yap.sendmoney.databinding.ItemRecentBeneficiariesTransferBindingImpl;
import co.yap.sendmoney.databinding.ItemRecentBeneficiaryBindingImpl;
import co.yap.sendmoney.databinding.LayoutBankDetailToolbarBindingImpl;
import co.yap.sendmoney.databinding.LayoutBeneficiairySearchBindingImpl;
import co.yap.sendmoney.databinding.LayoutBeneficiariesBindingImpl;
import co.yap.sendmoney.databinding.LayoutBeneficiaryTransferToolBarBindingImpl;
import co.yap.sendmoney.databinding.LayoutItemBeneficiaryBindingImpl;
import co.yap.sendmoney.databinding.LayoutNoContactsBindingImpl;
import co.yap.sendmoney.databinding.LayoutRecentBeneficiariesBindingImpl;
import co.yap.sendmoney.databinding.LayoutSendBeneficiariesToolbarBindingImpl;
import co.yap.sendmoney.databinding.LayoutSendMoneyHomeToolbarBindingImpl;
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
  private static final int LAYOUT_ACTIVITYBENEFICIARYCASHTRANSFER = 1;

  private static final int LAYOUT_ACTIVITYEDITBENEFICIARY = 2;

  private static final int LAYOUT_ACTIVITYSENDMONEYHOME = 3;

  private static final int LAYOUT_ACTIVITYSENDMONEYLANDING = 4;

  private static final int LAYOUT_BOTTOMSHEETINVITEFRIEND = 5;

  private static final int LAYOUT_BOTTOMSHEETPOP = 6;

  private static final int LAYOUT_FRAGMENTADDBANKDETAIL = 7;

  private static final int LAYOUT_FRAGMENTADDBENEFICIARYCASHFLOW = 8;

  private static final int LAYOUT_FRAGMENTADDBENEFICIARYDOMESTICTRANSFER = 9;

  private static final int LAYOUT_FRAGMENTADDBENEFICIARYINTERNATIONALBANKTRANSFER = 10;

  private static final int LAYOUT_FRAGMENTBENEFICIARYACCOUNTDETAIL = 11;

  private static final int LAYOUT_FRAGMENTBENEFICIARYOVERVIEW = 12;

  private static final int LAYOUT_FRAGMENTCASHTRANSFER = 13;

  private static final int LAYOUT_FRAGMENTCASHTRANSFERCONFIRMATION = 14;

  private static final int LAYOUT_FRAGMENTINTERNATIONALFUNDSTRANSFER = 15;

  private static final int LAYOUT_FRAGMENTINTERNATIONALTRANSACTIONCONFIRMATION = 16;

  private static final int LAYOUT_FRAGMENTSELECTCOUNTRY = 17;

  private static final int LAYOUT_FRAGMENTTRANSFERSUCCESS = 18;

  private static final int LAYOUT_FRAGMENTTRANSFERTYPE = 19;

  private static final int LAYOUT_ITEMBANKPARAMS = 20;

  private static final int LAYOUT_ITEMBENEFICIARIES = 21;

  private static final int LAYOUT_ITEMRAKBANK = 22;

  private static final int LAYOUT_ITEMREASONLIST = 23;

  private static final int LAYOUT_ITEMRECENTBENEFICIARIESTRANSFER = 24;

  private static final int LAYOUT_ITEMRECENTBENEFICIARY = 25;

  private static final int LAYOUT_LAYOUTBANKDETAILTOOLBAR = 26;

  private static final int LAYOUT_LAYOUTBENEFICIAIRYSEARCH = 27;

  private static final int LAYOUT_LAYOUTBENEFICIARIES = 28;

  private static final int LAYOUT_LAYOUTBENEFICIARYTRANSFERTOOLBAR = 29;

  private static final int LAYOUT_LAYOUTITEMBENEFICIARY = 30;

  private static final int LAYOUT_LAYOUTNOCONTACTS = 31;

  private static final int LAYOUT_LAYOUTRECENTBENEFICIARIES = 32;

  private static final int LAYOUT_LAYOUTSENDBENEFICIARIESTOOLBAR = 33;

  private static final int LAYOUT_LAYOUTSENDMONEYHOMETOOLBAR = 34;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(34);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.activity_beneficiary_cash_transfer, LAYOUT_ACTIVITYBENEFICIARYCASHTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.activity_edit_beneficiary, LAYOUT_ACTIVITYEDITBENEFICIARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.activity_send_money_home, LAYOUT_ACTIVITYSENDMONEYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.activity_send_money_landing, LAYOUT_ACTIVITYSENDMONEYLANDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.bottom_sheet_invite_friend, LAYOUT_BOTTOMSHEETINVITEFRIEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.bottom_sheet_pop, LAYOUT_BOTTOMSHEETPOP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_add_bank_detail, LAYOUT_FRAGMENTADDBANKDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_add_beneficiary_cash_flow, LAYOUT_FRAGMENTADDBENEFICIARYCASHFLOW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_add_beneficiary_domestic_transfer, LAYOUT_FRAGMENTADDBENEFICIARYDOMESTICTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_add_beneficiary_international_bank_transfer, LAYOUT_FRAGMENTADDBENEFICIARYINTERNATIONALBANKTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_beneficiary_account_detail, LAYOUT_FRAGMENTBENEFICIARYACCOUNTDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_beneficiary_overview, LAYOUT_FRAGMENTBENEFICIARYOVERVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_cash_transfer, LAYOUT_FRAGMENTCASHTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_cash_transfer_confirmation, LAYOUT_FRAGMENTCASHTRANSFERCONFIRMATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_international_funds_transfer, LAYOUT_FRAGMENTINTERNATIONALFUNDSTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_international_transaction_confirmation, LAYOUT_FRAGMENTINTERNATIONALTRANSACTIONCONFIRMATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_select_country, LAYOUT_FRAGMENTSELECTCOUNTRY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_transfer_success, LAYOUT_FRAGMENTTRANSFERSUCCESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.fragment_transfer_type, LAYOUT_FRAGMENTTRANSFERTYPE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_bank_params, LAYOUT_ITEMBANKPARAMS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_beneficiaries, LAYOUT_ITEMBENEFICIARIES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_rak_bank, LAYOUT_ITEMRAKBANK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_reason_list, LAYOUT_ITEMREASONLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_recent_beneficiaries_transfer, LAYOUT_ITEMRECENTBENEFICIARIESTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.item_recent_beneficiary, LAYOUT_ITEMRECENTBENEFICIARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_bank_detail_toolbar, LAYOUT_LAYOUTBANKDETAILTOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_beneficiairy_search, LAYOUT_LAYOUTBENEFICIAIRYSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_beneficiaries, LAYOUT_LAYOUTBENEFICIARIES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_beneficiary_transfer_tool_bar, LAYOUT_LAYOUTBENEFICIARYTRANSFERTOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_item_beneficiary, LAYOUT_LAYOUTITEMBENEFICIARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_no_contacts, LAYOUT_LAYOUTNOCONTACTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_recent_beneficiaries, LAYOUT_LAYOUTRECENTBENEFICIARIES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_send_beneficiaries_toolbar, LAYOUT_LAYOUTSENDBENEFICIARIESTOOLBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(co.yap.sendmoney.R.layout.layout_send_money_home_toolbar, LAYOUT_LAYOUTSENDMONEYHOMETOOLBAR);
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
        case  LAYOUT_ACTIVITYBENEFICIARYCASHTRANSFER: {
          if ("layout/activity_beneficiary_cash_transfer_0".equals(tag)) {
            return new ActivityBeneficiaryCashTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_beneficiary_cash_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYEDITBENEFICIARY: {
          if ("layout/activity_edit_beneficiary_0".equals(tag)) {
            return new ActivityEditBeneficiaryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_edit_beneficiary is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSENDMONEYHOME: {
          if ("layout/activity_send_money_home_0".equals(tag)) {
            return new ActivitySendMoneyHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_send_money_home is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSENDMONEYLANDING: {
          if ("layout/activity_send_money_landing_0".equals(tag)) {
            return new ActivitySendMoneyLandingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_send_money_landing is invalid. Received: " + tag);
        }
        case  LAYOUT_BOTTOMSHEETINVITEFRIEND: {
          if ("layout/bottom_sheet_invite_friend_0".equals(tag)) {
            return new BottomSheetInviteFriendBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bottom_sheet_invite_friend is invalid. Received: " + tag);
        }
        case  LAYOUT_BOTTOMSHEETPOP: {
          if ("layout/bottom_sheet_pop_0".equals(tag)) {
            return new BottomSheetPopBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for bottom_sheet_pop is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADDBANKDETAIL: {
          if ("layout/fragment_add_bank_detail_0".equals(tag)) {
            return new FragmentAddBankDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_add_bank_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADDBENEFICIARYCASHFLOW: {
          if ("layout/fragment_add_beneficiary_cash_flow_0".equals(tag)) {
            return new FragmentAddBeneficiaryCashFlowBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_add_beneficiary_cash_flow is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADDBENEFICIARYDOMESTICTRANSFER: {
          if ("layout/fragment_add_beneficiary_domestic_transfer_0".equals(tag)) {
            return new FragmentAddBeneficiaryDomesticTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_add_beneficiary_domestic_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADDBENEFICIARYINTERNATIONALBANKTRANSFER: {
          if ("layout/fragment_add_beneficiary_international_bank_transfer_0".equals(tag)) {
            return new FragmentAddBeneficiaryInternationalBankTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_add_beneficiary_international_bank_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTBENEFICIARYACCOUNTDETAIL: {
          if ("layout/fragment_beneficiary_account_detail_0".equals(tag)) {
            return new FragmentBeneficiaryAccountDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_beneficiary_account_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTBENEFICIARYOVERVIEW: {
          if ("layout/fragment_beneficiary_overview_0".equals(tag)) {
            return new FragmentBeneficiaryOverviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_beneficiary_overview is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCASHTRANSFER: {
          if ("layout/fragment_cash_transfer_0".equals(tag)) {
            return new FragmentCashTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_cash_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCASHTRANSFERCONFIRMATION: {
          if ("layout/fragment_cash_transfer_confirmation_0".equals(tag)) {
            return new FragmentCashTransferConfirmationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_cash_transfer_confirmation is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTINTERNATIONALFUNDSTRANSFER: {
          if ("layout/fragment_international_funds_transfer_0".equals(tag)) {
            return new FragmentInternationalFundsTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_international_funds_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTINTERNATIONALTRANSACTIONCONFIRMATION: {
          if ("layout/fragment_international_transaction_confirmation_0".equals(tag)) {
            return new FragmentInternationalTransactionConfirmationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_international_transaction_confirmation is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSELECTCOUNTRY: {
          if ("layout/fragment_select_country_0".equals(tag)) {
            return new FragmentSelectCountryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_select_country is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTRANSFERSUCCESS: {
          if ("layout/fragment_transfer_success_0".equals(tag)) {
            return new FragmentTransferSuccessBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_transfer_success is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTRANSFERTYPE: {
          if ("layout/fragment_transfer_type_0".equals(tag)) {
            return new FragmentTransferTypeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_transfer_type is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMBANKPARAMS: {
          if ("layout/item_bank_params_0".equals(tag)) {
            return new ItemBankParamsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_bank_params is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMBENEFICIARIES: {
          if ("layout/item_beneficiaries_0".equals(tag)) {
            return new ItemBeneficiariesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_beneficiaries is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRAKBANK: {
          if ("layout/item_rak_bank_0".equals(tag)) {
            return new ItemRakBankBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_rak_bank is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMREASONLIST: {
          if ("layout/item_reason_list_0".equals(tag)) {
            return new ItemReasonListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_reason_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRECENTBENEFICIARIESTRANSFER: {
          if ("layout/item_recent_beneficiaries_transfer_0".equals(tag)) {
            return new ItemRecentBeneficiariesTransferBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_recent_beneficiaries_transfer is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRECENTBENEFICIARY: {
          if ("layout/item_recent_beneficiary_0".equals(tag)) {
            return new ItemRecentBeneficiaryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_recent_beneficiary is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBANKDETAILTOOLBAR: {
          if ("layout/layout_bank_detail_toolbar_0".equals(tag)) {
            return new LayoutBankDetailToolbarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_bank_detail_toolbar is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBENEFICIAIRYSEARCH: {
          if ("layout/layout_beneficiairy_search_0".equals(tag)) {
            return new LayoutBeneficiairySearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_beneficiairy_search is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBENEFICIARIES: {
          if ("layout/layout_beneficiaries_0".equals(tag)) {
            return new LayoutBeneficiariesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_beneficiaries is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBENEFICIARYTRANSFERTOOLBAR: {
          if ("layout/layout_beneficiary_transfer_tool_bar_0".equals(tag)) {
            return new LayoutBeneficiaryTransferToolBarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_beneficiary_transfer_tool_bar is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTITEMBENEFICIARY: {
          if ("layout/layout_item_beneficiary_0".equals(tag)) {
            return new LayoutItemBeneficiaryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_item_beneficiary is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTNOCONTACTS: {
          if ("layout/layout_no_contacts_0".equals(tag)) {
            return new LayoutNoContactsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_no_contacts is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTRECENTBENEFICIARIES: {
          if ("layout/layout_recent_beneficiaries_0".equals(tag)) {
            return new LayoutRecentBeneficiariesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_recent_beneficiaries is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSENDBENEFICIARIESTOOLBAR: {
          if ("layout/layout_send_beneficiaries_toolbar_0".equals(tag)) {
            return new LayoutSendBeneficiariesToolbarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_send_beneficiaries_toolbar is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSENDMONEYHOMETOOLBAR: {
          if ("layout/layout_send_money_home_toolbar_0".equals(tag)) {
            return new LayoutSendMoneyHomeToolbarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_send_money_home_toolbar is invalid. Received: " + tag);
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
    static final SparseArray<String> sKeys = new SparseArray<String>(117);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "accountIban");
      sKeys.put(2, "accountNo");
      sKeys.put(3, "accountNumber");
      sKeys.put(4, "accountUuid");
      sKeys.put(5, "adapter");
      sKeys.put(6, "addressTitle");
      sKeys.put(7, "amount");
      sKeys.put(8, "amountBackground");
      sKeys.put(9, "availableBalanceString");
      sKeys.put(10, "bankAddress");
      sKeys.put(11, "bankBranch");
      sKeys.put(12, "bankCity");
      sKeys.put(13, "bankName");
      sKeys.put(14, "bankPhoneNumber");
      sKeys.put(15, "beneficiary");
      sKeys.put(16, "beneficiaryAccountNumber");
      sKeys.put(17, "beneficiaryCountry");
      sKeys.put(18, "beneficiaryId");
      sKeys.put(19, "beneficiaryType");
      sKeys.put(20, "branchAddress");
      sKeys.put(21, "branchName");
      sKeys.put(22, "buttonText");
      sKeys.put(23, "buttonTitle");
      sKeys.put(24, "cardSerialNumber");
      sKeys.put(25, "color");
      sKeys.put(26, "confirmHeading");
      sKeys.put(27, "confirmIban");
      sKeys.put(28, "confirmPin");
      sKeys.put(29, "country");
      sKeys.put(30, "country2DigitIsoCode");
      sKeys.put(31, "countryBankRequirementFieldCode");
      sKeys.put(32, "countryCode");
      sKeys.put(33, "currency");
      sKeys.put(34, "currencyType");
      sKeys.put(35, "cutOffTimeMsg");
      sKeys.put(36, "dataCountry");
      sKeys.put(37, "dialerError");
      sKeys.put(38, "drawbleRight");
      sKeys.put(39, "editBeneficiaryViewModel");
      sKeys.put(40, "error");
      sKeys.put(41, "errorDescription");
      sKeys.put(42, "errorMessage");
      sKeys.put(43, "etInputAmount");
      sKeys.put(44, "etOutputAmount");
      sKeys.put(45, "feeAmountSpannableString");
      sKeys.put(46, "firstName");
      sKeys.put(47, "flagDrawableResId");
      sKeys.put(48, "flagLayoutVisibility");
      sKeys.put(49, "flowType");
      sKeys.put(50, "forgotTextVisibility");
      sKeys.put(51, "frameActivityViewModel");
      sKeys.put(52, "fromFxRate");
      sKeys.put(53, "fullName");
      sKeys.put(54, "hideSwiftSection");
      sKeys.put(55, "iban");
      sKeys.put(56, "id");
      sKeys.put(57, "idCode");
      sKeys.put(58, "identifierCode1");
      sKeys.put(59, "identifierCode2");
      sKeys.put(60, "imageUrl");
      sKeys.put(61, "lastName");
      sKeys.put(62, "lastUsedDate");
      sKeys.put(63, "loading");
      sKeys.put(64, "maxLimit");
      sKeys.put(65, "minLimit");
      sKeys.put(66, "mobile");
      sKeys.put(67, "mobileNo");
      sKeys.put(68, "mobileNoLength");
      sKeys.put(69, "needIban");
      sKeys.put(70, "needOverView");
      sKeys.put(71, "newPin");
      sKeys.put(72, "nickName");
      sKeys.put(73, "noteValue");
      sKeys.put(74, "oldPin");
      sKeys.put(75, "otp");
      sKeys.put(76, "otpDataModel");
      sKeys.put(77, "otpType");
      sKeys.put(78, "passcode");
      sKeys.put(79, "phoneNumber");
      sKeys.put(80, "pincode");
      sKeys.put(81, "populateSpinnerData");
      sKeys.put(82, "position");
      sKeys.put(83, "produceCode");
      sKeys.put(84, "reasonList");
      sKeys.put(85, "receivingAmountDescription");
      sKeys.put(86, "recentTransferItemVM");
      sKeys.put(87, "rightButtonText");
      sKeys.put(88, "selectedBeneficiaryType");
      sKeys.put(89, "selectedCountry");
      sKeys.put(90, "sequence");
      sKeys.put(91, "showIban");
      sKeys.put(92, "similar");
      sKeys.put(93, "subTitle");
      sKeys.put(94, "successHeader");
      sKeys.put(95, "swiftCode");
      sKeys.put(96, "timer");
      sKeys.put(97, "title");
      sKeys.put(98, "titleSetPin");
      sKeys.put(99, "toFxRate");
      sKeys.put(100, "toast");
      sKeys.put(101, "toolBarTitle");
      sKeys.put(102, "toolbarTitle");
      sKeys.put(103, "toolbarVisibility");
      sKeys.put(104, "transactionData");
      sKeys.put(105, "transferAmountHeading");
      sKeys.put(106, "transferDescription");
      sKeys.put(107, "transferFeeDescription");
      sKeys.put(108, "transferFeeSpannable");
      sKeys.put(109, "transferType");
      sKeys.put(110, "valid");
      sKeys.put(111, "validResend");
      sKeys.put(112, "verificationDescription");
      sKeys.put(113, "verificationDescriptionForLogo");
      sKeys.put(114, "verificationTitle");
      sKeys.put(115, "viewModel");
      sKeys.put(116, "webViewFragmentViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(34);

    static {
      sKeys.put("layout/activity_beneficiary_cash_transfer_0", co.yap.sendmoney.R.layout.activity_beneficiary_cash_transfer);
      sKeys.put("layout/activity_edit_beneficiary_0", co.yap.sendmoney.R.layout.activity_edit_beneficiary);
      sKeys.put("layout/activity_send_money_home_0", co.yap.sendmoney.R.layout.activity_send_money_home);
      sKeys.put("layout/activity_send_money_landing_0", co.yap.sendmoney.R.layout.activity_send_money_landing);
      sKeys.put("layout/bottom_sheet_invite_friend_0", co.yap.sendmoney.R.layout.bottom_sheet_invite_friend);
      sKeys.put("layout/bottom_sheet_pop_0", co.yap.sendmoney.R.layout.bottom_sheet_pop);
      sKeys.put("layout/fragment_add_bank_detail_0", co.yap.sendmoney.R.layout.fragment_add_bank_detail);
      sKeys.put("layout/fragment_add_beneficiary_cash_flow_0", co.yap.sendmoney.R.layout.fragment_add_beneficiary_cash_flow);
      sKeys.put("layout/fragment_add_beneficiary_domestic_transfer_0", co.yap.sendmoney.R.layout.fragment_add_beneficiary_domestic_transfer);
      sKeys.put("layout/fragment_add_beneficiary_international_bank_transfer_0", co.yap.sendmoney.R.layout.fragment_add_beneficiary_international_bank_transfer);
      sKeys.put("layout/fragment_beneficiary_account_detail_0", co.yap.sendmoney.R.layout.fragment_beneficiary_account_detail);
      sKeys.put("layout/fragment_beneficiary_overview_0", co.yap.sendmoney.R.layout.fragment_beneficiary_overview);
      sKeys.put("layout/fragment_cash_transfer_0", co.yap.sendmoney.R.layout.fragment_cash_transfer);
      sKeys.put("layout/fragment_cash_transfer_confirmation_0", co.yap.sendmoney.R.layout.fragment_cash_transfer_confirmation);
      sKeys.put("layout/fragment_international_funds_transfer_0", co.yap.sendmoney.R.layout.fragment_international_funds_transfer);
      sKeys.put("layout/fragment_international_transaction_confirmation_0", co.yap.sendmoney.R.layout.fragment_international_transaction_confirmation);
      sKeys.put("layout/fragment_select_country_0", co.yap.sendmoney.R.layout.fragment_select_country);
      sKeys.put("layout/fragment_transfer_success_0", co.yap.sendmoney.R.layout.fragment_transfer_success);
      sKeys.put("layout/fragment_transfer_type_0", co.yap.sendmoney.R.layout.fragment_transfer_type);
      sKeys.put("layout/item_bank_params_0", co.yap.sendmoney.R.layout.item_bank_params);
      sKeys.put("layout/item_beneficiaries_0", co.yap.sendmoney.R.layout.item_beneficiaries);
      sKeys.put("layout/item_rak_bank_0", co.yap.sendmoney.R.layout.item_rak_bank);
      sKeys.put("layout/item_reason_list_0", co.yap.sendmoney.R.layout.item_reason_list);
      sKeys.put("layout/item_recent_beneficiaries_transfer_0", co.yap.sendmoney.R.layout.item_recent_beneficiaries_transfer);
      sKeys.put("layout/item_recent_beneficiary_0", co.yap.sendmoney.R.layout.item_recent_beneficiary);
      sKeys.put("layout/layout_bank_detail_toolbar_0", co.yap.sendmoney.R.layout.layout_bank_detail_toolbar);
      sKeys.put("layout/layout_beneficiairy_search_0", co.yap.sendmoney.R.layout.layout_beneficiairy_search);
      sKeys.put("layout/layout_beneficiaries_0", co.yap.sendmoney.R.layout.layout_beneficiaries);
      sKeys.put("layout/layout_beneficiary_transfer_tool_bar_0", co.yap.sendmoney.R.layout.layout_beneficiary_transfer_tool_bar);
      sKeys.put("layout/layout_item_beneficiary_0", co.yap.sendmoney.R.layout.layout_item_beneficiary);
      sKeys.put("layout/layout_no_contacts_0", co.yap.sendmoney.R.layout.layout_no_contacts);
      sKeys.put("layout/layout_recent_beneficiaries_0", co.yap.sendmoney.R.layout.layout_recent_beneficiaries);
      sKeys.put("layout/layout_send_beneficiaries_toolbar_0", co.yap.sendmoney.R.layout.layout_send_beneficiaries_toolbar);
      sKeys.put("layout/layout_send_money_home_toolbar_0", co.yap.sendmoney.R.layout.layout_send_money_home_toolbar);
    }
  }
}
