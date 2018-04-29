package inter;

import inter.logic.RouterLogic;

import java.util.Scanner;

public class InterMain {
    private static RouterLogic routerLogic;

    public static void main(String[] args) {
        routerLogic = new RouterLogic();
        Scanner in = new Scanner(System.in);
        String s = in.next();
    }
}
