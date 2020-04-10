package com.android.library.coachmark.configuration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020\u0018J\u0006\u0010%\u001a\u00020 J\u0006\u0010&\u001a\u00020\u0018J\u000e\u0010\'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u0010J\u000e\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u0014J\u0012\u0010+\u001a\u00020\u00182\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/android/library/coachmark/configuration/CoachMarkSequence;", "", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mCoachMark", "Lcom/android/library/coachmark/components/CoachMarkOverlay;", "getMCoachMark", "()Lcom/android/library/coachmark/components/CoachMarkOverlay;", "setMCoachMark", "(Lcom/android/library/coachmark/components/CoachMarkOverlay;)V", "mCoachMarkOverlayClickListener", "Lcom/android/library/coachmark/components/CoachMarkOverlay$OverlayClickListener;", "mCoachMarkSkipButtonClickListener", "Lcom/android/library/coachmark/components/CoachMarkSkipButton$ButtonClickListener;", "mSequenceConfig", "Lcom/android/library/coachmark/configuration/CoachMarkConfig;", "mSequenceItem", "Lcom/android/library/coachmark/components/CoachMarkOverlay$Builder;", "mSequenceListener", "Lcom/android/library/coachmark/components/listener/SequenceListener;", "mSequenceQueue", "Ljava/util/Queue;", "addItem", "", "targetCoordinates", "Landroid/graphics/Rect;", "infoText", "", "targetView", "Landroid/view/View;", "position", "", "coachMarkBuilder", "addOverlayClickListener", "", "clearList", "getRemainingListCount", "setNextView", "setSequenceConfig", "config", "setSequenceListener", "listener", "start", "rootView", "Landroid/view/ViewGroup;", "coachmark_debug"})
public final class CoachMarkSequence {
    private final java.util.Queue<com.android.library.coachmark.components.CoachMarkOverlay.Builder> mSequenceQueue = null;
    private com.android.library.coachmark.configuration.CoachMarkConfig mSequenceConfig;
    private com.android.library.coachmark.components.listener.SequenceListener mSequenceListener;
    private com.android.library.coachmark.components.CoachMarkOverlay.Builder mSequenceItem;
    @org.jetbrains.annotations.Nullable()
    private com.android.library.coachmark.components.CoachMarkOverlay mCoachMark;
    private final com.android.library.coachmark.components.CoachMarkSkipButton.ButtonClickListener mCoachMarkSkipButtonClickListener = null;
    private final com.android.library.coachmark.components.CoachMarkOverlay.OverlayClickListener mCoachMarkOverlayClickListener = null;
    private final android.content.Context mContext = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.android.library.coachmark.components.CoachMarkOverlay getMCoachMark() {
        return null;
    }
    
    public final void setMCoachMark(@org.jetbrains.annotations.Nullable()
    com.android.library.coachmark.components.CoachMarkOverlay p0) {
    }
    
    public final void setSequenceConfig(@org.jetbrains.annotations.NotNull()
    com.android.library.coachmark.configuration.CoachMarkConfig config) {
    }
    
    public final void setNextView() {
    }
    
    public final void setSequenceListener(@org.jetbrains.annotations.NotNull()
    com.android.library.coachmark.components.listener.SequenceListener listener) {
    }
    
    public final int getRemainingListCount() {
        return 0;
    }
    
    public final void addItem(@org.jetbrains.annotations.NotNull()
    android.view.View targetView, @org.jetbrains.annotations.NotNull()
    java.lang.String infoText) {
    }
    
    public final void addItem(@org.jetbrains.annotations.NotNull()
    android.view.View targetView, @org.jetbrains.annotations.NotNull()
    java.lang.String infoText, int position) {
    }
    
    public final void addItem(@org.jetbrains.annotations.NotNull()
    android.graphics.Rect targetCoordinates, @org.jetbrains.annotations.NotNull()
    java.lang.String infoText) {
    }
    
    public final void addItem(@org.jetbrains.annotations.NotNull()
    com.android.library.coachmark.components.CoachMarkOverlay.Builder coachMarkBuilder, boolean addOverlayClickListener) {
    }
    
    public final void clearList() {
    }
    
    public final void start(@org.jetbrains.annotations.Nullable()
    android.view.ViewGroup rootView) {
    }
    
    public CoachMarkSequence(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext) {
        super();
    }
}