package com.example.produtosapi.domain.dto;

// jarkarta é o antigo javax
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// Record: utilizada para transferencia de dados, são imutaveis
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {
}
