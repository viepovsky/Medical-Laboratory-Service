package com.viepovsky.examination.dto;

import com.viepovsky.examination.ExaminationType;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationResponse {
    private Long id;

    private String name;

    private ExaminationType type;

    private String shortDescription;

    private String longDescription;

    private BigDecimal cost;
}
