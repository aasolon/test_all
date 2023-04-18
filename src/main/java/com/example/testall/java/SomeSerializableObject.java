package com.example.testall.java;

import java.io.Serializable;

public class SomeSerializableObject implements Serializable {
    private String hash;
    private String userLogin;
    private String orgId;
    private String requestId;

    /**
     * IP-адрес клиента УПШ, отправившего запрос
     */
    private String ip;

    /**
     * Глобальный идентификатор операции для отслеживания синхронной и асинхронной обработки
     */
    private String operationId;

    /**
     * IP-адрес шлюза УПШ (web-сервера), принявшего запрос от клиента
     */
    private String upgGateIp;

    /**
     * Время получения клиентского запроса
     */
    private Long requestReceiptTimestamp;

    public SomeSerializableObject(String hash, String userLogin, String orgId, String requestId, String ip, String operationId
                          ,String upgGateIp, Long requestReceiptTimestamp) {
        this.hash = hash;
        this.userLogin = userLogin;
        this.orgId = orgId;
        this.requestId = requestId;
        this.ip = ip;
        this.operationId = operationId;
        this.upgGateIp = upgGateIp;
        this.requestReceiptTimestamp = requestReceiptTimestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getIp() {
        return ip;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getUpgGateIp() {
        return upgGateIp;
    }

    public Long getRequestReceiptTimestamp() {
        return requestReceiptTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SomeSerializableObject that = (SomeSerializableObject) o;

        if (hash != null ? !hash.equals(that.hash) : that.hash != null) {
            return false;
        }
        if (!orgId.equals(that.orgId)) {
            return false;
        }
        if (!requestId.equals(that.requestId)) {
            return false;
        }
        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) {
            return false;
        }

        if (ip != null ? !ip.equals(that.ip) : that.ip != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = hash != null ? hash.hashCode() : 0;
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + orgId.hashCode();
        result = 31 * result + requestId.hashCode();
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestPayload{" +
                "hash='" + hash + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", orgId='" + orgId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", ip='" + ip + '\'' +
                ", operationId='" + operationId + '\'' +
                ", upgGateIp='" + upgGateIp + '\'' +
                ", requestReceiptTimestamp='" + requestReceiptTimestamp + '\'' +
                '}';
    }
}
