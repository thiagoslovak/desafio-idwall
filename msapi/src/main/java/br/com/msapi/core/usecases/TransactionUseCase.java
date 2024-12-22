package br.com.msapi.core.usecases;

import java.time.LocalDate;

public interface TransactionUseCase {
    boolean idValidation(Long id);
    boolean initialDateValidation(LocalDate initialDate);
    boolean finalDateValidation(LocalDate finalDate);
    boolean validateConsultationTime(LocalDate initialDate, LocalDate finalDate, Long maximumTime);
    boolean validateMinimumDeadlineExternalConsultation(LocalDate initialDate, LocalDate finalDate, Long deadlineExternalConsult);
}
