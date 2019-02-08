package company;

import java.util.Comparator;

public enum Credit {
    $(100), QUARTER(25), DIME(10), NICKEL(5);

    private int value;

    Credit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    class CreditComparator implements Comparator<Credit> {

        @Override
        public int compare(Credit o1, Credit o2) {
            return Integer.compare(o1.getValue(), o2.getValue());
        }
    }
}
