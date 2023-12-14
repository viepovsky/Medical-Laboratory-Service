package com.viepovsky.examination;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ExaminationService {
    private final ExaminationRepository examinationRepository;

    List<Examination> getAllExaminations() {
        return examinationRepository.findAll();
    }

    Examination getExamination(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Examination with given id: " + id + " does not exist in database."));
    }

    Examination saveExamination(Examination examination) {
        return examinationRepository.save(examination);
    }

    void updateExamination(Examination examination) {
        var retrievedExamination = examinationRepository.findById(examination.getId())
                .orElseThrow(() -> new EntityNotFoundException("Examination with given id: " + examination.getId() + " does not exist in database."));
        retrievedExamination.updateFrom(examination);
        examinationRepository.save(retrievedExamination);
    }

    void deleteExamination(Long id) {
        if (examinationRepository.existsById(id)) {
            examinationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Examination with given id: " + id + " does not exist in database.");
        }
    }
}
