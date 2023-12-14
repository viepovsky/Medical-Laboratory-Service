package com.viepovsky.examination;

import com.viepovsky.examination.dto.ExaminationRequest;
import com.viepovsky.examination.dto.ExaminationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical/examinations")
@Validated
class ExaminationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationController.class);
    private final ExaminationService examinationService;
    private final ExaminationMapper mapper;

    @GetMapping
    ResponseEntity<List<ExaminationResponse>> getAllExaminations() {
        LOGGER.info("Get all examinations endpoint used.");
        List<Examination> examinations = examinationService.getAllExaminations();
        return ResponseEntity.ok(mapper.mapToExaminationResponseList(examinations));
    }

    @GetMapping("/{id}")
    ResponseEntity<ExaminationResponse> getExamination(@PathVariable @Min(1) Long id) {
        LOGGER.info("Get examination endpoint used with id:{}", id);
        Examination examination = examinationService.getExamination(id);
        return ResponseEntity.ok(mapper.mapToExaminationResponse(examination));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<ExaminationResponse> saveExamination(@RequestBody @Valid ExaminationRequest request) {
        LOGGER.info("Create examination endpoint used with examination name:{}", request.getName());
        Examination toSave = mapper.mapToExamination(request);
        ExaminationResponse saved = mapper.mapToExaminationResponse(examinationService.saveExamination(toSave));
        return ResponseEntity.created(URI.create("/medical/examinations/" + saved.getId())).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<Void> updateExamination(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid ExaminationRequest request
    ) {
        LOGGER.info("Update examination endpoint used with id:{}", id);
        Examination examination = mapper.mapToExamination(request);
        examination.setId(id);
        examinationService.updateExamination(examination);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExamination(@PathVariable @Min(1) Long id) {
        LOGGER.info("Delete examination endpoint used for id:{}", id);
        examinationService.deleteExamination(id);
        return ResponseEntity.ok().build();
    }
}
