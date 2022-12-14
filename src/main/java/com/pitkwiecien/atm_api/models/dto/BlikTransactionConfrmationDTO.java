package com.pitkwiecien.atm_api.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BlikTransactionConfrmationDTO {
    private int fromAccountId;
    private int confirmedTransactionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateOfConfirmation;

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getConfirmedTransactionId() {
        return confirmedTransactionId;
    }

    public void setConfirmedTransactionId(int confirmedTransactionId) {
        this.confirmedTransactionId = confirmedTransactionId;
    }

    public Date getDateOfConfirmation() {
        return dateOfConfirmation;
    }

    public void setDateOfConfirmation(Date dateOfConfirmation) {
        this.dateOfConfirmation = dateOfConfirmation;
    }
}
