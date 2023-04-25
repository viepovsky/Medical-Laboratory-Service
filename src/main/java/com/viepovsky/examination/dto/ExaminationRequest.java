package com.viepovsky.examination.dto;

import com.viepovsky.examination.ExaminationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationRequest {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotNull(message = "Type must not be null")
    private ExaminationType type;

    @NotBlank(message = "Short description must not be empty")
    private String shortDescription;

    private String longDescription;

    @NotNull(message = "Cost must not be null")
    private BigDecimal cost;
}
