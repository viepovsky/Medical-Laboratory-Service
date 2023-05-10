package com.viepovsky.examination;

import com.viepovsky.examination.dto.ExaminationRequest;
import com.viepovsky.examination.dto.ExaminationResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExaminationMapperTest {
    private final ExaminationMapper mapper = new ExaminationMapper();

    @Test
    void should_map_Examination_to_ExaminationDTO() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").type(ExaminationType.BLOOD).cost(BigDecimal.valueOf(50)).build();
        //When
        var mappedExamination = mapper.mapToExaminationResponse(examination);
        //Then
        assertThat(mappedExamination).isNotNull();
        assertEquals(examination.getId(), mappedExamination.getId());
        assertEquals(examination.getType(), mappedExamination.getType());
        assertEquals(examination.getCost(), mappedExamination.getCost());
    }

    @Test
    void should_map_Examination_list_to_ExaminationDTO_list() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").type(ExaminationType.BLOOD).cost(BigDecimal.valueOf(50)).build();
        List<Examination> examinations = new ArrayList<>(List.of(examination));
        //When
        List<ExaminationResponse> mappedExaminations = mapper.mapToExaminationResponseList(examinations);
        //Then
        assertThat(mappedExaminations).isNotNull();
        assertEquals(examinations.size(), mappedExaminations.size());
    }

    @Test
    void should_map_ExaminationDTO_to_Examination() {
        //Given
        var examination = ExaminationRequest.builder().name("Test").type(ExaminationType.BLOOD).cost(BigDecimal.valueOf(50)).build();
        //When
        var mappedExamination = mapper.mapToExamination(examination);
        //Then
        assertThat(mappedExamination).isNotNull();
        assertEquals(examination.getName(), mappedExamination.getName());
        assertEquals(examination.getType(), mappedExamination.getType());
        assertEquals(examination.getCost(), mappedExamination.getCost());
    }
}