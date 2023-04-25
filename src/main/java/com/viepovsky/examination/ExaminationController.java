package com.viepovsky.examination;

import com.viepovsky.examination.dto.ExaminationRequest;
import com.viepovsky.examination.dto.ExaminationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical/examinations")
@Validated
class ExaminationController {
    private static final Logger logger = LoggerFactory.getLogger(ExaminationController.class);
    private final ExaminationService service;
    private final ExaminationMapper mapper;

    @GetMapping
    ResponseEntity<List<ExaminationResponse>> getAllExaminations() {
        logger.info("getAllExaminations endpoint used");
        List<Examination> examinations = service.getAllExaminations();
        return ResponseEntity.ok(mapper.mapToExaminationResponseList(examinations));
    }

    @GetMapping("/{id}")
    ResponseEntity<ExaminationResponse> getExamination(@PathVariable @Min(1) Long id) {
        logger.info("getExamination endpoint used with id value: " + id);
        Examination examination = service.getExamination(id);
        return ResponseEntity.ok(mapper.mapToExaminationResponse(examination));
    }

    @PostMapping
    ResponseEntity<ExaminationResponse> saveExamination(@RequestBody @Valid ExaminationRequest request) {
        logger.info("createExamination endpoint used with body: " + request.toString());
        Examination toSave = mapper.mapToExamination(request);
        ExaminationResponse saved = mapper.mapToExaminationResponse(service.saveExamination(toSave));
        return ResponseEntity.created(URI.create("/medical/examinations/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateExamination(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid ExaminationRequest request
    ) {
        logger.info("updateExamination endpoint used with id: " + id + " and body: " + request.toString());
        Examination examination = mapper.mapToExamination(request);
        examination.setId(id);
        service.updateExamination(examination);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExamination(@PathVariable @Min(1) Long id) {
        service.deleteExamination(id);
        return ResponseEntity.ok().build();
    }
}
