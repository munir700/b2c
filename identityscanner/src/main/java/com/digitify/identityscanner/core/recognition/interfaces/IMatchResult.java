package com.digitify.identityscanner.core.recognition.interfaces;

import com.digitify.identityscanner.core.detection.interfaces.IResult;

public interface IMatchResult extends IResult {
    boolean isMatched();
}
