package inter.domain;

import inter.domain.enums.InterState;

import java.io.Serializable;

public class InterFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private InterTransaction transaction;
    private InterState state;
    private String message;

    private InterFeedback() {}

    public InterFeedback(InterTransaction transaction, InterState state, String message) {
        this.transaction = transaction;
        this.state = state;
        this.message = message;
    }

    public InterTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(InterTransaction transaction) {
        this.transaction = transaction;
    }

    public InterState getState() {
        return state;
    }

    public void setState(InterState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
