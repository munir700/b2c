package com.digitify.identityscanner.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.digitify.identityscanner.BR;

import java.util.List;

public class AppUIBinder {

    @BindingAdapter({"bitmap"})
    public static void setImageBitmap(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter({"preText"})
    public static void setPreText(TextView view, int pre) {
        Context con = view.getContext();
        view.setText(con.getString(pre));
    }

    @BindingAdapter({"postText"})
    public static void setPostText(TextView view, int post) {
        Context con = view.getContext();
        view.setText(con.getString(post));
    }

    @BindingAdapter({"postText"})
    public static void setPostText(TextView view, String post) {
        view.setText(post);
    }

    @BindingAdapter({"preText"})
    public static void setPreText(TextView view, String pre) {
        view.setText(pre);
    }

    @BindingAdapter({"postText", "preText"})
    public static void setPrePostText(TextView view, String post, String pre) {
        view.setText(pre + ": " + post);
    }

    @BindingAdapter({"postText", "preText"})
    public static void setPrePostText(TextView view, int post, int pre) {
        Context con = view.getContext();
        setPrePostText(view, con.getString(post), con.getString(pre));
    }

    @BindingAdapter({"postText", "preText"})
    public static void setPrePostText(TextView view, String post, int pre) {
        Context con = view.getContext();
        setPrePostText(view, post, con.getString(pre));
    }

    @BindingAdapter({"postText", "preText"})
    public static void setPrePostText(TextView view, int post, String pre) {
        Context con = view.getContext();
        setPrePostText(view, con.getString(post), pre);
    }

    @BindingAdapter({"title", "subtitle"})
    public static void setTitleSubtitleText(TextView view, String title, String subtitle) {
        String total = title + ": " + subtitle;
        Spannable spannable = new SpannableString(total);
        // spannable.setSpan(new RelativeSizeSpan(0.9f), 0, total.indexOf(":"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), total.indexOf(":") + 1, total.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spannable);
    }


    @BindingAdapter({"entries", "layout"})
    public static <T> void setEntries(ViewGroup viewGroup,
                                      List<T> entries, int layoutId) {
        viewGroup.removeAllViews();
        if (entries != null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < entries.size(); i++) {
                T entry = entries.get(i);
                ViewDataBinding binding = DataBindingUtil
                        .inflate(inflater, layoutId, viewGroup, true);
                binding.setVariable(BR.data, entry);
            }
        }
    }

}
