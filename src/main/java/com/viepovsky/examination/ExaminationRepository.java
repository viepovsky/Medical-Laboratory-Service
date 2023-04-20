package com.viepovsky.examination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
