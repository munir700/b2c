package com.digitify.identityscanner.core.detection.interfaces;

public interface Analyser<T extends Analysable> {
    void analyse(T object);
}
