package rabo;

import abn.logic.AbnMessageLogic;
import rabo.logic.RaboMessageLogic;

import java.util.Scanner;

public class RaboMain {
    private static RaboMessageLogic raboMessageLogic;
    public static void main(String[] args) {
        raboMessageLogic = new RaboMessageLogic();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String i = in.next();
            raboMessageLogic.sendTransaction(
                    "NLRABO0123456781",
                    "NLABNA0123456782",
                    5.5);
        }
    }
}
