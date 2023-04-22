package com.viepovsky.examination;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
        //When
        //Then
    }

    @Test
    void should_get_Examination_by_id() {
        //Given
        //When
        //Then
    }

    @Test
    void should_not_get_Examination_if_given_id_doesnt_exist() {
        //Given
        //When
        //Then
    }

    @Test
    void should_save_Examination() {
        //Given
        //When
        //Then
    }

    @Test
    void should_update_Examination() {
        //Given
        //When
        //Then
    }

    @Test
    void should_not_update_Examination_if_given_id_doesnt_exist() {
        //Given
        //When
        //Then
    }

    @Test
    void should_delete_Examination() {
        //Given
        //When
        //Then
    }

    @Test
    void should_not_delete_Examination_if_given_id_doesnt_exist() {
        //Given
        //When
        //Then
    }
}