package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {



    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();

        System.out.println("Welcome to the Vending Machine. ");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String command = sc.nextLine();
            switch (command) {
                case "COIN_RETURN":
                    vendingMachine.coinReturn();
                    break;
                case "SERVICE":
                    vendingMachine.setVendingMachineState(VendingMachineState.SERVICE_IN_PROGRESS);
                    break;
                case "SERVICE_STOP":
                    vendingMachine.setVendingMachineState(VendingMachineState.AVAILABLE);
                    break;
                case "A":
                    vendingMachine.stockUpItem(Item.AVOCADO);
                    break;
                case "B":
                    vendingMachine.stockUpItem(Item.BEER);
                    break;
                case "C":
                    vendingMachine.stockUpItem(Item.CHOCOLATE);
                    break;
                case "GET-A":
                    vendingMachine.purchase(Item.AVOCADO);
                    break;
                case "GET-B":
                    vendingMachine.purchase(Item.BEER);
                    break;
                case "GET-C":
                    vendingMachine.purchase(Item.CHOCOLATE);
                    break;
                case "N":
                    vendingMachine.addCoin(Money.NICKEL);
                    break;
                case "D":
                    vendingMachine.addCoin(Money.DIME);
                    break;
                case "Q":
                    vendingMachine.addCoin(Money.QUARTER);
                    break;
                case "$":
                    vendingMachine.addCoin(Money.$);
                    break;
                default:
                    System.out.println("Failed to understand your input");
                    break;

            }
        }
    }
}
//SERVICE -> setState(OPEN)
// Q
// Q
// $
