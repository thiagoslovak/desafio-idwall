package br.com.msapi.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponse {

    private Long categoryId;
    private String categoryName;
    private Long purchaseId;
    private BigDecimal moneySpent;
    private Timestamp purchaseDate;
    private Timestamp verison;
}
