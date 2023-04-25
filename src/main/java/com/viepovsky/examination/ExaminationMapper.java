package com.viepovsky.examination;

import com.viepovsky.examination.dto.ExaminationRequest;
import com.viepovsky.examination.dto.ExaminationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ExaminationMapper {
    ExaminationResponse mapToExaminationResponse(Examination examination) {
        return new ExaminationResponse(
                examination.getId(),
                examination.getName(),
                examination.getType(),
                examination.getShortDescription(),
                examination.getLongDescription(),
                examination.getCost()
        );
    }

    List<ExaminationResponse> mapToExaminationResponseList(List<Examination> examinations) {
        return examinations.stream()
                .map(this::mapToExaminationResponse)
                .toList();
    }

    Examination mapToExamination(ExaminationRequest request) {
        return Examination.builder()
                .name(request.getName())
                .type(request.getType())
                .shortDescription(request.getShortDescription())
                .longDescription(request.getLongDescription())
                .cost(request.getCost())
                .build();
    }
}
