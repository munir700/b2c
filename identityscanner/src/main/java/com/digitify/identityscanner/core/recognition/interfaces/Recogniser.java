package com.digitify.identityscanner.core.recognition.interfaces;

import com.digitify.identityscanner.core.detection.interfaces.IResult;

public interface Recogniser<T extends Recognisable, V extends IResult> {
    V recognise(T source);
}
