package com.viepovsky.diagnostic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum DiagnosticType {
    BLOOD("Blood test results"),
    URINE("Urine test results"),
    STOOL("Stool test results"),
    THROAT("Throat swab test results");
    private final String type;
}
