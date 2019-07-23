package com.digitify.identityscanner.core.detection.interfaces;

import com.digitify.identityscanner.core.detection.models.Face;

public interface IExpressionsAnalyser<T extends IExpressionable> extends Analyser<T> {

    boolean isRightEyeOpen(Face face);

    boolean isLeftEyeOpen(Face face);

    boolean isSmiling(Face face);
}
