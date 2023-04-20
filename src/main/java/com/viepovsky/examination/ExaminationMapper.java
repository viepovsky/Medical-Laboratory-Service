package com.viepovsky.examination;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ExaminationMapper {
    ExaminationDTO mapToExaminationDto(Examination examination) {
        return new ExaminationDTO(
                examination.getId(),
                examination.getName(),
                examination.getType(),
                examination.getShortDescription(),
                examination.getLongDescription(),
                examination.getCost()
        );
    }

    List<ExaminationDTO> mapToExaminationDtoList(List<Examination> examinations) {
        return examinations.stream()
                .map(this::mapToExaminationDto)
                .toList();
    }

    Examination mapToExamination(ExaminationDTO examinationDTO) {
        return Examination.builder()
                .name(examinationDTO.getName())
                .type(examinationDTO.getType())
                .shortDescription(examinationDTO.getShortDescription())
                .longDescription(examinationDTO.getLongDescription())
                .cost(examinationDTO.getCost())
                .build();
    }
}
