package company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VendingMachine {
    private final Map<Credit, Integer> creditsInserted;
    private final Map<Item, Integer> availableItems;
    private final Map<Credit, Integer> availableChange;
    private VendingMachineState vendingMachineState;

    VendingMachine() {

        this.creditsInserted = new HashMap<>();
        resetCurrentlyInsertedMoney();


        this.availableItems = new HashMap<>();
        availableItems.put(Item.AVOCADO, 10);
        availableItems.put(Item.CHOCOLATE, 10);
        availableItems.put(Item.BEER, 10);

        this.availableChange = new HashMap<>();
        availableChange.put(Credit.DIME, 2);
        availableChange.put(Credit.QUARTER, 2);
        availableChange.put(Credit.$, 2);
        availableChange.put(Credit.NICKEL, 2);

        this.vendingMachineState = VendingMachineState.AVAILABLE;
    }

    void coinReturn() {
        System.out.println(creditsInserted);
        resetCurrentlyInsertedMoney();
    }

    void addCoin(Credit credit) {
        if (vendingMachineState == VendingMachineState.AVAILABLE) {
            creditsInserted.put(credit, creditsInserted.get(credit) + 1);
            System.out.println("You have inserted " + sumOfMoney(creditsInserted));
//            System.out.println(creditsInserted.get(credit));
        } else {
            availableChange.put(credit, availableChange.get(credit) + 1);
        }

    }

    void purchase(Item item) {
        int itemStock = availableItems.get(item);
        if (itemStock == 0) {
            System.out.println("Sorry, we are out of the item you are looking for");
            return;
        }
        if (item.getPrice() > sumOfMoney(creditsInserted)) {
            System.out.println("It does not look like you have enough money to buy this item");
            return;
        }

        final int moneyToReturn = calculateMoneyToReturn(item);

        moveInsertedCreditsToAvailableChange();

        Map<Credit, Integer> change = calculateChange(moneyToReturn);

        availableItems.put(item, itemStock - 1);

        System.out.println(item + " " + change);
    }

    private int calculateMoneyToReturn(Item item) {
        return sumOfMoney(creditsInserted) - item.getPrice();
    }

    private Map<Credit, Integer> calculateChange(int moneyToReturn) {
        Map<Credit, Integer> change = new HashMap<>();

        availableChange.keySet().stream()
                .sorted(Comparator.comparingInt(Credit::getValue).reversed())
                .filter(credit -> credit != Credit.$)
                .filter(credit -> credit.getValue() < moneyToReturn)
                .forEach(credit -> {
                    int amountToReturn = (int) Math.floor((moneyToReturn - sumOfMoney(change)) / credit.getValue());

                    availableChange.put(credit, availableChange.get(credit) - amountToReturn);
                    change.put(credit, amountToReturn);
                });
        return change;
    }

    private void moveInsertedCreditsToAvailableChange() {
        creditsInserted.entrySet()
                .forEach(moneyAmountEntry -> {
                    int currentlyAvailableChangeOfCoin = availableChange.get(moneyAmountEntry.getKey());
                    availableChange.put(moneyAmountEntry.getKey(), currentlyAvailableChangeOfCoin + moneyAmountEntry.getValue());
                });
        resetCurrentlyInsertedMoney();
    }

    void setVendingMachineState(VendingMachineState vendingMachineState) {
        System.out.println("Currently available stock: " + availableItems);
        System.out.println("Currently available change: " + availableChange);
        System.out.println("Setting the vending machine to state: " + vendingMachineState);
        this.vendingMachineState = vendingMachineState;
    }

    void stockUpItem(Item item) {
        if (vendingMachineState != VendingMachineState.SERVICE_IN_PROGRESS) {
            System.out.println("Sorry, it's not possible to buy anything right now");
            return;
        }
        availableItems.put(item, availableItems.get(item) + 1);
    }

    private int sumOfMoney(Map<Credit, Integer> map) {
        Integer reduce = map.entrySet().stream()
                .map((entry) -> entry.getKey().getValue() * entry.getValue())
                .reduce(0, (accumulated, next) -> accumulated += next);
        return reduce;
    }

    private void resetCurrentlyInsertedMoney() {
        Stream.of(Credit.values())
                .forEach(credit -> {
                    creditsInserted.put(credit, 0);
                });
    }

    public void listItem() {
        System.out.println(availableItems);
    }
}
