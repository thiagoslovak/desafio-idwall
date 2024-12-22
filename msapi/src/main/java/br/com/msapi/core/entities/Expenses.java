package br.com.msapi.core.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "tb_expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expense")
    private Long idexpense;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "purchase_id")
    private Long purchaseId;

    @Column(name = "money_spent")
    private BigDecimal moneySpent;

    @Column(name = "purchase_date")
    private Timestamp purchaseDate;

    @Version
    @Column(name = "verison")
    private Timestamp verison;
}
