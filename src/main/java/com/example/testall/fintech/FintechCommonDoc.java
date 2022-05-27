package com.example.testall.fintech;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

public abstract class FintechCommonDoc {

    private String number;

    @NotNull
    private Date date;

    @Null
    private String bankStatus;

    @Null
    private String bankComment;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getBankComment() {
        return bankComment;
    }

    public void setBankComment(String bankComment) {
        this.bankComment = bankComment;
    }
}
