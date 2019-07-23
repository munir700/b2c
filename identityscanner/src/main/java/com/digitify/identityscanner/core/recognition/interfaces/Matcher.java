package com.digitify.identityscanner.core.recognition.interfaces;

import com.digitify.identityscanner.core.detection.interfaces.IResult;

public interface Matcher<T extends Matchable, V extends IResult> {
    V match(T source, T target);
}
