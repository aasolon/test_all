package com.example.testall.fintech;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class FintechPayment extends FintechCommonDoc {
    @NotNull
    private UUID externalId;

    @NotNull @DecimalMin("0.01") @Digits(integer = 16, fraction = 2)
    @JsonSerialize(using = FintechMoneyJsonSerializer.class)
    private BigDecimal amount;

    /* Общие поля документа - дополнительные */
    @NotNull @Pattern(regexp = "^01$")
    private String operationCode;

    @Pattern(regexp = "^(электронно|срочно|0)$")
    private String deliveryKind;

    @Size(max = 2)
    private String incomeTypeCode;

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getDeliveryKind() {
        return deliveryKind;
    }

    public void setDeliveryKind(String deliveryKind) {
        this.deliveryKind = deliveryKind;
    }

    public String getIncomeTypeCode() {
        return incomeTypeCode;
    }

    public void setIncomeTypeCode(String incomeTypeCode) {
        this.incomeTypeCode = incomeTypeCode;
    }
}
