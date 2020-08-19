package co.yap.multicurrency.helper;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001f\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lco/yap/multicurrency/helper/UIBindingAdapters;", "", "()V", "spanColor", "", "view", "Landroidx/appcompat/widget/AppCompatTextView;", "currency", "", "updateIconTint", "Landroidx/appcompat/widget/AppCompatImageView;", "update", "", "(Landroidx/appcompat/widget/AppCompatImageView;Ljava/lang/Boolean;)V", "multicurrency_debug"})
public final class UIBindingAdapters {
    public static final co.yap.multicurrency.helper.UIBindingAdapters INSTANCE = null;
    
    @androidx.databinding.BindingAdapter(value = {"updateIcon"})
    public static final void updateIconTint(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.AppCompatImageView view, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean update) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"spanColor"})
    public static final void spanColor(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.AppCompatTextView view, @org.jetbrains.annotations.NotNull()
    java.lang.String currency) {
    }
    
    private UIBindingAdapters() {
        super();
    }
}