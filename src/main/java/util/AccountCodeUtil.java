package util;

public class AccountCodeUtil {
    public String getCodeFromAccount(String accountNumber) {
        return accountNumber.substring(0, 5);
    }
}
