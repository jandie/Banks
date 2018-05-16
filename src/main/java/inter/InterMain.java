package inter;

import inter.logic.CacheProcessLogic;
import inter.logic.RouterLogic;

import java.util.Scanner;

public class InterMain {
    private static RouterLogic routerLogic;

    public static void main(String[] args) {
        routerLogic = new RouterLogic();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String i = in.next();
            System.out.println("Processing cache...");
            new CacheProcessLogic(routerLogic).processCache();
            System.out.println("Done!");
        }
    }
}
