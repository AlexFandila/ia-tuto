package com.tutorial.ia.infrastructure.repository;

import com.tutorial.ia.domain.entities.QuestionAnswerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerEntryRepository extends JpaRepository<QuestionAnswerEntry, Long>  {
}
