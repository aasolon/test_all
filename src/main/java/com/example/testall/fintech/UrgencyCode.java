package com.example.testall.fintech;

import org.apache.commons.lang3.StringUtils;

public enum UrgencyCode {
    /**
     * Срочный
     */
    INTERNAL(1, "INTERNAL", "Срочный"),
    /**
     * Срочный УВ
     */
    INTERNAL_NOTIF(2, "INTERNAL_NOTIF", "Срочный УВ"),
    /**
     * Неотложный
     */
    OFFHOURS(3, "OFFHOURS", "Неотложный"),
    /**
     * По системе БЕСП (Поле deliveryType=БЭСП)
     */
    BESP(4, "BESP", "БЭСП"),
    /**
     * без указания срочности
     */
    NORMAL(5, "NORMAL", null);


    private final int code;
    private final String urgencyCode;
    private final String urgencyName;

    UrgencyCode(int code, String urgencyCode, String urgencyName) {
        this.code = code;
        this.urgencyCode = urgencyCode;
        this.urgencyName = urgencyName;
    }

    public int getCode() {
        return code;
    }

    public String getUrgencyCode() {
        return urgencyCode;
    }

    public String getUrgencyName() {
        return urgencyName;
    }


    public static UrgencyCode getUrgencyCode(Boolean urgent, String urgentList, Boolean press, String sendType) {
        if (urgent != null && urgent && !StringUtils.isEmpty(urgentList) && urgentList.equals(INTERNAL.getUrgencyName())) {
            return UrgencyCode.INTERNAL;
        } else if (urgent != null && urgent && !StringUtils.isEmpty(urgentList) && urgentList.equals(INTERNAL_NOTIF.getUrgencyName())) {
            return UrgencyCode.INTERNAL_NOTIF;
        } else if (press != null && press) {
            return UrgencyCode.OFFHOURS;
        } else if (!StringUtils.isEmpty(sendType) && sendType.equals(BESP.getUrgencyName())) {
            return UrgencyCode.BESP;
        }
        return UrgencyCode.NORMAL;
    }

}
