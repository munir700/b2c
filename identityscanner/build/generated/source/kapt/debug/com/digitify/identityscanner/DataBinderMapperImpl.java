package com.digitify.identityscanner;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.digitify.identityscanner.databinding.FragmentCameraBindingImpl;
import com.digitify.identityscanner.databinding.FragmentDocReviewBindingImpl;
import com.digitify.identityscanner.databinding.FragmentSelfieVideoBindingImpl;
import com.digitify.identityscanner.databinding.ItemTextDoubleLinesBindingImpl;
import com.digitify.identityscanner.databinding.ItemTextTitleSubtitleBindingImpl;
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
  private static final int LAYOUT_FRAGMENTCAMERA = 1;

  private static final int LAYOUT_FRAGMENTDOCREVIEW = 2;

  private static final int LAYOUT_FRAGMENTSELFIEVIDEO = 3;

  private static final int LAYOUT_ITEMTEXTDOUBLELINES = 4;

  private static final int LAYOUT_ITEMTEXTTITLESUBTITLE = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.digitify.identityscanner.R.layout.fragment_camera, LAYOUT_FRAGMENTCAMERA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.digitify.identityscanner.R.layout.fragment_doc_review, LAYOUT_FRAGMENTDOCREVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.digitify.identityscanner.R.layout.fragment_selfie_video, LAYOUT_FRAGMENTSELFIEVIDEO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.digitify.identityscanner.R.layout.item_text_double_lines, LAYOUT_ITEMTEXTDOUBLELINES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.digitify.identityscanner.R.layout.item_text_title_subtitle, LAYOUT_ITEMTEXTTITLESUBTITLE);
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
        case  LAYOUT_FRAGMENTCAMERA: {
          if ("layout/fragment_camera_0".equals(tag)) {
            return new FragmentCameraBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_camera is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTDOCREVIEW: {
          if ("layout/fragment_doc_review_0".equals(tag)) {
            return new FragmentDocReviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_doc_review is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSELFIEVIDEO: {
          if ("layout/fragment_selfie_video_0".equals(tag)) {
            return new FragmentSelfieVideoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_selfie_video is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTEXTDOUBLELINES: {
          if ("layout/item_text_double_lines_0".equals(tag)) {
            return new ItemTextDoubleLinesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_text_double_lines is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTEXTTITLESUBTITLE: {
          if ("layout/item_text_title_subtitle_0".equals(tag)) {
            return new ItemTextTitleSubtitleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_text_title_subtitle is invalid. Received: " + tag);
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
    result.add(new co.yap.translation.DataBinderMapperImpl());
    result.add(new co.yap.yapcore.DataBinderMapperImpl());
    result.add(new org.opencv.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(50);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "confirmPin");
      sKeys.put(2, "color");
      sKeys.put(3, "cardSerialNumber");
      sKeys.put(4, "buttonTitle");
      sKeys.put(5, "error");
      sKeys.put(6, "validResend");
      sKeys.put(7, "valid");
      sKeys.put(8, "timer");
      sKeys.put(9, "subTitle");
      sKeys.put(10, "verificationDescription");
      sKeys.put(11, "forgotTextVisibility");
      sKeys.put(12, "similar");
      sKeys.put(13, "pincode");
      sKeys.put(14, "oldPin");
      sKeys.put(15, "dialerError");
      sKeys.put(16, "otp");
      sKeys.put(17, "loading");
      sKeys.put(18, "newPin");
      sKeys.put(19, "verificationTitle");
      sKeys.put(20, "sequence");
      sKeys.put(21, "toast");
      sKeys.put(22, "titleSetPin");
      sKeys.put(23, "toolbarTitle");
      sKeys.put(24, "viewModel");
      sKeys.put(25, "passcode");
      sKeys.put(26, "submitButtonTitle");
      sKeys.put(27, "personalInfo");
      sKeys.put(28, "instructions");
      sKeys.put(29, "documentInfo");
      sKeys.put(30, "data");
      sKeys.put(31, "flashMessage");
      sKeys.put(32, "docValid");
      sKeys.put(33, "stepInstructions");
      sKeys.put(34, "recordingButtonState");
      sKeys.put(35, "scanMode");
      sKeys.put(36, "title");
      sKeys.put(37, "blinkValidationText");
      sKeys.put(38, "result");
      sKeys.put(39, "voiceValidationText");
      sKeys.put(40, "faceValidationText");
      sKeys.put(41, "faceBitmap");
      sKeys.put(42, "cardRect");
      sKeys.put(43, "capturing");
      sKeys.put(44, "previewBitmap");
      sKeys.put(45, "warning");
      sKeys.put(46, "processing");
      sKeys.put(47, "model");
      sKeys.put(48, "reviewText");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/fragment_camera_0", com.digitify.identityscanner.R.layout.fragment_camera);
      sKeys.put("layout/fragment_doc_review_0", com.digitify.identityscanner.R.layout.fragment_doc_review);
      sKeys.put("layout/fragment_selfie_video_0", com.digitify.identityscanner.R.layout.fragment_selfie_video);
      sKeys.put("layout/item_text_double_lines_0", com.digitify.identityscanner.R.layout.item_text_double_lines);
      sKeys.put("layout/item_text_title_subtitle_0", com.digitify.identityscanner.R.layout.item_text_title_subtitle);
    }
  }
}
