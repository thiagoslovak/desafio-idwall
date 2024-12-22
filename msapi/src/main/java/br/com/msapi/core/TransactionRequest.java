package br.com.msapi.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class TransactionRequest {

    private Long id;
    private String initialDate;
    private String finalDate;

    public LocalDate getInitialDateAsLocalDate() {
        return StringUtils.isNotBlank(initialDate) ? LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    public LocalDate getFinalDateAsLocalDate() {
        return StringUtils.isNotBlank(finalDate) ? LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public TransactionRequest(Long id, String initialDate, String finalDate) {
        this.id = id;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }
}
