package com.example.appquanly;

public class ResponseSQL {
    private long id;
    private boolean isSuccess;

    public ResponseSQL(long id, boolean isSuccess) {
        this.id = id;
        this.isSuccess = isSuccess;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
