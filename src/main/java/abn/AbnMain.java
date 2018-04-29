package abn;

import abn.logic.AbnMessageLogic;

public class AbnMain {
    public static void main(String[] args) {
        AbnMessageLogic abnMessageLogic = new AbnMessageLogic();

        abnMessageLogic.sendTransaction(
                "NLABNA0123456781",
                "NLABNA0123456782",
                1.25);
    }
}
