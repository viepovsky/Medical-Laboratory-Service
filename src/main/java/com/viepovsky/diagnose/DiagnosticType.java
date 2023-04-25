package com.viepovsky.diagnose;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiagnosticType {
    BLOOD("Blood test results"),
    URINE("Urine test results"),
    STOOL("Stool test results"),
    THROAT("Throat swab test results");
    private final String type;
}
