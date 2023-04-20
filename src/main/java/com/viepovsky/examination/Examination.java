package com.viepovsky.examination;

import com.viepovsky.utilities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXAMINATIONS")
class Examination extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examinations_seq")
    @SequenceGenerator(name = "examinations_seq", initialValue = 500, allocationSize = 1)
    private Long id;

    private String name;

    private ExaminationType type;

    private String shortDescription;

    private String longDescription;

    private BigDecimal cost;

    void updateFrom(Examination examination) {
        name = Optional.ofNullable(examination.getName()).orElse(name);
        type = Optional.ofNullable(examination.getType()).orElse(type);
        shortDescription = Optional.ofNullable(examination.getShortDescription()).orElse(shortDescription);
        longDescription = Optional.ofNullable(examination.getLongDescription()).orElse(longDescription);
        cost = Optional.ofNullable(examination.getCost()).orElse(cost);
    }
}
