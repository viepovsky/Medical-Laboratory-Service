package com.viepovsky.examination;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ExaminationService {
    private final ExaminationRepository repository;

    List<Examination> getAllExaminations() {
        return repository.findAll();
    }

    Examination getExamination(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Examination with given id: " + id + " does not exist in database."));
    }

    Examination createExamination(Examination examination) {
        return repository.save(examination);
    }

    void updateExamination(Examination examination) {
        var retrievedExamination = repository.findById(examination.getId())
                .orElseThrow(() -> new EntityNotFoundException("Examination with given id: " + examination.getId() + " does not exist in database."));
        retrievedExamination.updateFrom(examination);
        repository.save(retrievedExamination);
    }

    void deleteExamination(Long id) {
        var examination = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Examination with given id: " + id + " does not exist in database."));
        repository.delete(examination);
    }
}
