package com.digitify.identityscanner.core.detection.interfaces;

public interface Validator<T extends Validatable> {
    boolean validate(T object);
}
