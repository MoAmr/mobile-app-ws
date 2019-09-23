package com.appsdevblog.app.ws.domain_response;

public class OperationStatusModel {

    private String OperationResult;
    private String OperationName;

    public String getOperationResult() {
        return OperationResult;
    }

    public void setOperationResult(String operationResult) {
        OperationResult = operationResult;
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }
}
