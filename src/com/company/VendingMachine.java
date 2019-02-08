package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VendingMachine {
    private final Map<Money, Integer> currentlyInsertedMoney;
    private final Map<Item, Integer> availableItems;
    private final Map<Money, Integer> availableChange;
    private VendingMachineState vendingMachineState;

    public VendingMachine() {

        this.currentlyInsertedMoney = new HashMap<>();
        resetCurrentlyInsertedMoney();


        this.availableItems = new HashMap<>();
        availableItems.put(Item.AVOCADO, 10);
        availableItems.put(Item.CHOCOLATE, 10);
        availableItems.put(Item.BEER, 10);

        this.availableChange = new HashMap<>();
        availableChange.put(Money.DIME, 2);
        availableChange.put(Money.QUARTER, 2);
        availableChange.put(Money.$, 2);
        availableChange.put(Money.NICKEL, 2);

        this.vendingMachineState = VendingMachineState.AVAILABLE;
    }

    private void resetCurrentlyInsertedMoney() {
        Stream.of(Money.values())
                .forEach(money -> {
                    currentlyInsertedMoney.put(money, 0);
                });
    }

    public void coinReturn() {
        System.out.println(currentlyInsertedMoney);
        resetCurrentlyInsertedMoney();
    }

    public void returnChange() {

    }

    public void addCoin(Money money) {
        if (vendingMachineState == VendingMachineState.AVAILABLE) {
            int currentInsertedMoney = currentlyInsertedMoney.get(money);
            currentlyInsertedMoney.put(money, currentInsertedMoney + 1);
            System.out.println(currentlyInsertedMoney.get(money));
        } else {
            int currentAvailableChange = availableChange.get(money);
            availableChange.put(money, currentAvailableChange + 1);
        }

    }

    public void stockUpItem(Item item) {
        if (vendingMachineState != VendingMachineState.SERVICE_IN_PROGRESS) {
            throw new ServiceNotInProgressException();
        }
        int currentItemStock = availableItems.get(item);
        availableItems.put(item, currentItemStock + 1);
    }

    public void purchase(Item item) {
        int itemStock = availableItems.get(item);
        if (itemStock == 0) {
            throw new OutOfStockException();
        }
        if (item.getPrice() > sumOfMoney(currentlyInsertedMoney)) {
            throw new NotEnoughMoneyException();
        }

        final float moneyToReturn = sumOfMoney(currentlyInsertedMoney) - item.getPrice();

        currentlyInsertedMoney.entrySet()
                .forEach(entry -> {
                    int currentlyAvailableChangeOfCoin = availableChange.get(entry.getKey());
                    availableChange.put(entry.getKey(), currentlyAvailableChangeOfCoin + entry.getValue());
                });
        resetCurrentlyInsertedMoney();

        Map<Money, Integer> currentlyTakenChange = new HashMap<>();

            availableChange.keySet().stream()
                    .filter(money -> money != Money.$)
                    .filter(money -> money.getValue() > moneyToReturn)
                    .forEach(money -> {
                        int amountToReturn = (int) Math.floor((moneyToReturn - sumOfMoney(currentlyTakenChange))/money.getValue());

                        availableChange.put(money, availableChange.get(money) - amountToReturn);
                        currentlyTakenChange.put(money, amountToReturn);
                    });


        availableItems.put(item, itemStock - 1);


        System.out.println(availableItems.get(item));
        System.out.println(currentlyTakenChange);
        System.out.println("New available change");
        System.out.println(availableChange);

    }

    public void setVendingMachineState(VendingMachineState vendingMachineState) {
        this.vendingMachineState = vendingMachineState;
    }

    private float sumOfMoney(Map<Money, Integer> map) {
        Float reduce = map.entrySet().stream()
                .map((entry) -> entry.getKey().getValue() * entry.getValue())
                .reduce(0.0f, (accumulated, next) -> accumulated += next);
        System.out.println("You have inserted $" + reduce);
        return reduce;
    }

}
