package company;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    static final VendingMachine vendingMachine = new VendingMachine();

    public static void main(String[] args) {
        System.out.println("Welcome to the Vending Machine. ");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String inputCommand = sc.nextLine();
            Stream.of(inputCommand.split(","))
                    .forEach(command -> readCommand(command.toUpperCase().trim()));
        }
    }

    private static void readCommand(String command) {
        System.out.println(command);
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
                vendingMachine.addCoin(Credit.NICKEL);
                break;
            case "D":
                vendingMachine.addCoin(Credit.DIME);
                break;
            case "Q":
                vendingMachine.addCoin(Credit.QUARTER);
                break;
            case "LS":
                vendingMachine.listItem();
                break;
            case "$":
                vendingMachine.addCoin(Credit.$);
                break;
            default:
                System.out.println("Failed to understand your input");
                break;

        }
    }
}
//SERVICE -> setState(OPEN)
// Q
// Q
// $
