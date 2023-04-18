package com.example.testall.simplecontroller;

import java.util.Date;

public class FintechFileUploadRequest {

    private String type;

    private String subType;

    private String digestSignature;

    private Date date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDigestSignature() {
        return digestSignature;
    }

    public void setDigestSignature(String digestSignature) {
        this.digestSignature = digestSignature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
