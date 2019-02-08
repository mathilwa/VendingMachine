package company;

public enum Item {
    CHOCOLATE(65), BEER(100), AVOCADO(150);

    private final int price;

    Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
