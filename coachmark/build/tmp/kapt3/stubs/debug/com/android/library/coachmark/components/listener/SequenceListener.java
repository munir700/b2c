package com.android.library.coachmark.components.listener;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/android/library/coachmark/components/listener/SequenceListener;", "", "onNextItem", "", "coachMark", "Lcom/android/library/coachmark/components/CoachMarkOverlay;", "coachMarkSequence", "Lcom/android/library/coachmark/configuration/CoachMarkSequence;", "coachmark_debug"})
public abstract interface SequenceListener {
    
    public abstract void onNextItem(@org.jetbrains.annotations.NotNull()
    com.android.library.coachmark.components.CoachMarkOverlay coachMark, @org.jetbrains.annotations.NotNull()
    com.android.library.coachmark.configuration.CoachMarkSequence coachMarkSequence);
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 3)
    public final class DefaultImpls {
        
        public static void onNextItem(com.android.library.coachmark.components.listener.SequenceListener $this, @org.jetbrains.annotations.NotNull()
        com.android.library.coachmark.components.CoachMarkOverlay coachMark, @org.jetbrains.annotations.NotNull()
        com.android.library.coachmark.configuration.CoachMarkSequence coachMarkSequence) {
        }
    }
}