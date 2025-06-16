package minicla03.coinquylife.Expense.DOMAIN.model;

public enum CategoryExpense
{
    FOOD("Cibo"),
    TRANSPORT("Trasporti"),
    ENTERTAINMENT("Intrattenimento"),
    SHOPPING("Shopping"),
    HEALTH("Salute"),
    BILLS("Bollette"),
    OTHER("Altro");

    private final String name;

    CategoryExpense(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CategoryExpense fromString(String text)
    {
        for (CategoryExpense b : CategoryExpense.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Nessuna categoria trovata per la stringa: " + text);
    }

}
