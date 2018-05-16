package inter.domain;

import util.AccountCodeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InterTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String toAccount;
    private String fromAccount;
    private String toBankCode;
    private String fromBankCode;
    private double amount;
    private List<String> fromReferences;
    private long count;

    public InterTransaction() {}

    public InterTransaction(String toAccount, String fromAccount, double amount, List<String> fromReferences) {
        AccountCodeUtil accountCodeUtil = new AccountCodeUtil();

        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.fromReferences = fromReferences;
        this.count = 1;

        this.toBankCode = accountCodeUtil.getCodeFromAccount(toAccount);
        this.fromBankCode = accountCodeUtil.getCodeFromAccount(fromAccount);
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToBankCode() {
        return toBankCode;
    }

    public void setToBankCode(String toBankCode) {
        this.toBankCode = toBankCode;
    }

    public String getFromBankCode() {
        return fromBankCode;
    }

    public void setFromBankCode(String fromBankCode) {
        this.fromBankCode = fromBankCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public List<String> getFromReferences() {
        return fromReferences;
    }

    public void setFromReferences(List<String> fromReferences) {
        this.fromReferences = fromReferences;
    }

    public void addFromReferences(List<String> fromReferences) {
        this.fromReferences.addAll(fromReferences);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void increaseCount() {
        this.count ++;
    }

    @Override
    public String toString() {
        return "Transaction from: " + fromAccount + " to: " + toAccount + " amount: " + amount;
    }
}
