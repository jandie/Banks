package abn.domain;

import abn.domain.enums.AbnState;

import java.io.Serializable;

public class AbnFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private AbnTransaction transaction;
    private AbnState state;
    private String message;

    private AbnFeedback() {}

    public AbnFeedback(AbnTransaction transaction, AbnState state, String message) {
        this.transaction = transaction;
        this.state = state;
        this.message = message;
    }

    public AbnTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(AbnTransaction transaction) {
        this.transaction = transaction;
    }

    public AbnState getState() {
        return state;
    }

    public void setState(AbnState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
