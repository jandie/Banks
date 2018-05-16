package abn.domain;

import abn.domain.enums.AbnState;
import util.AccountCodeUtil;

import java.io.Serializable;
import java.util.List;

public class AbnTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String toAccount;
    private String fromAccount;
    private String toBankCode;
    private String fromBankCode;
    private double amount;
    private List<String> fromReferences;
    private AbnState state;

    public AbnTransaction() {}

    public AbnTransaction(String toAccount, String fromAccount, double amount, List<String> fromReferences) {
        AccountCodeUtil accountCodeUtil = new AccountCodeUtil();

        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.fromReferences = fromReferences;
        this.state = AbnState.SENT;

        this.toBankCode = accountCodeUtil.getCodeFromAccount(toAccount);
        this.fromBankCode = accountCodeUtil.getCodeFromAccount(fromAccount);
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToBankCode() {
        return toBankCode;
    }

    public String getFromBankCode() {
        return fromBankCode;
    }

    public double getAmount() {
        return amount;
    }

    public AbnState getState() {
        return state;
    }

    public void setState(AbnState state) {
        this.state = state;
    }

    public List<String> getFromReferences() {
        return fromReferences;
    }

    public void setFromReferences(List<String> fromReferences) {
        this.fromReferences = fromReferences;
    }

    @Override
    public String toString() {
        return "Transaction from: " + fromAccount + ", to: " + toAccount + ", amount: " + amount + ", state: " + state;
    }
}
