package com.viepovsky.examination;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class ExaminationDTO {
    private Long id;

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
