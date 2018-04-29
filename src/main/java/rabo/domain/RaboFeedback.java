package rabo.domain;


import rabo.domain.enums.RaboState;

import java.io.Serializable;

public class RaboFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private RaboTransaction transaction;
    private RaboState state;
    private String message;

    private RaboFeedback() {}

    public RaboFeedback(RaboTransaction transaction, RaboState state, String message) {
        this.transaction = transaction;
        this.state = state;
        this.message = message;
    }

    public RaboTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RaboTransaction transaction) {
        this.transaction = transaction;
    }

    public RaboState getState() {
        return state;
    }

    public void setState(RaboState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
