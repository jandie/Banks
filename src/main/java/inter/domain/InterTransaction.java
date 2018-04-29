package inter.domain;

import util.AccountCodeUtil;

import java.io.Serializable;

public class InterTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String toAccount;
    private String fromAccount;
    private String toBankCode;
    private String fromBankCode;
    private double amount;
    private String fromReference;

    public InterTransaction() {}

    public InterTransaction(String toAccount, String fromAccount, double amount, String fromReference) {
        AccountCodeUtil accountCodeUtil = new AccountCodeUtil();

        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.fromReference = fromReference;

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

    public String getFromReference() {
        return fromReference;
    }

    public void setFromReference(String fromReference) {
        this.fromReference = fromReference;
    }

    @Override
    public String toString() {
        return "Transaction from: " + fromAccount + " to: " + toAccount + " amount: " + amount;
    }
}
