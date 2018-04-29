package abn.domain;

import util.AccountCodeUtil;

import java.io.Serializable;

public class AbnTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String toAccount;
    private String fromAccount;
    private String toBankCode;
    private String fromBankCode;
    private long amount;

    public AbnTransaction(String toAccount, String fromAccount, long amount) {
        AccountCodeUtil accountCodeUtil = new AccountCodeUtil();

        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.amount = amount;

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

    public long getAmount() {
        return amount;
    }
}
