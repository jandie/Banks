package abn;

import abn.logic.AbnMessageLogic;

import java.util.Scanner;

public class AbnMain {
    private static AbnMessageLogic abnMessageLogic;
    public static void main(String[] args) {
        abnMessageLogic = new AbnMessageLogic();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String i = in.next();
            abnMessageLogic.sendTransaction(
                    "NLABNA0123456781",
                    "NLRABO0123456782",
                    1.25);
        }
    }
}
