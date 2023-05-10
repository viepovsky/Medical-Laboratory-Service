package com.viepovsky.examination;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExaminationServiceTest {
    @InjectMocks
    private ExaminationService service;
    @Mock
    private ExaminationRepository repository;

    @Test
    void should_get_all_Examinations() {
        //Given
        List<Examination> examinations = List.of(Mockito.mock(Examination.class));
        when(repository.findAll()).thenReturn(examinations);
        //When
        List<Examination> retrievedExaminations = service.getAllExaminations();
        //Then
        assertThat(retrievedExaminations).isNotNull();
        assertEquals(examinations.size(), retrievedExaminations.size());
    }

    @Test
    void should_get_Examination_by_id() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(examination));
        //When
        var retrievedExamination = service.getExamination(examination.getId());
        //Then
        assertThat(retrievedExamination).isNotNull();
        assertEquals(examination.getId(), retrievedExamination.getId());
        assertEquals(examination.getName(), retrievedExamination.getName());
    }

    @Test
    void should_not_get_Examination_if_given_id_doesnt_exist() {
        //Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //When && then
        assertThrows(EntityNotFoundException.class, () -> service.getExamination(5L));
    }

    @Test
    void should_save_Examination() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").build();
        when(repository.save(any(Examination.class))).thenReturn(examination);
        //When
        var retrievedExamination = service.saveExamination(examination);
        //Then
        assertThat(retrievedExamination).isNotNull();
        assertEquals(examination.getId(), retrievedExamination.getId());
        assertEquals(examination.getName(), retrievedExamination.getName());
    }

    @Test
    void should_update_Examination() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(examination));
        when(repository.save(any(Examination.class))).thenReturn(examination);
        //When
        service.updateExamination(examination);
        //Then
        verify(repository, times(1)).save(any(Examination.class));
    }

    @Test
    void should_not_update_Examination_if_given_id_doesnt_exist() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").build();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.updateExamination(examination));
    }

    @Test
    void should_delete_Examination() {
        //Given
        var examination = Examination.builder().id(5L).name("Test").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(examination));
        //When
        service.deleteExamination(examination.getId());
        //Then
        verify(repository, times(1)).delete(any(Examination.class));
    }

    @Test
    void should_not_delete_Examination_if_given_id_doesnt_exist() {
        //Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.deleteExamination(5L));
    }
}