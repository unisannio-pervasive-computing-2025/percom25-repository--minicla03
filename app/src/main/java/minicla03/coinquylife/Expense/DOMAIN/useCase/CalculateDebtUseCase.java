package minicla03.coinquylife.Expense.DOMAIN.useCase;

import minicla03.coinquylife.Expense.DOMAIN.model.Debt;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CalculateDebtUseCase implements ICalculateDebtUseCase
{
    private final IExpenseRepository repository;

    public CalculateDebtUseCase(IExpenseRepository repository)
    {
        this.repository = repository;
    }

    public void execute(String houseId, Consumer<List<String>> callback)
    {
        repository.calculateDebt(houseId, debts->{
            List<String> messages = new ArrayList<>();

            if (debts == null || debts.isEmpty()) {
                messages.add("💸 Nessun debito trovato");
                callback.accept(messages);
            }

            for (Debt debt : debts)
            {
                List<String> debtorNames = new ArrayList<>(debt.getParticipants().keySet());
                List<Double> amounts = new ArrayList<>(debt.getParticipants().values());

                boolean allSameAmount = true;
                double firstAmount = amounts.get(0);
                for (double amt : amounts) {
                    if (amt != firstAmount) {
                        allSameAmount = false;
                        break;
                    }
                }

                String message;

                if (allSameAmount) {
                    String formattedAmount = String.format("%.2f", firstAmount);
                    if (debtorNames.size() == 1) {
                        message = "💸 " + capitalize(debtorNames.get(0)) + " deve " + formattedAmount + "€ a " + debt.getCreatedBy();
                    } else if (debtorNames.size() == 2) {
                        message = "💸 " + capitalize(debtorNames.get(0)) + " e " + capitalize(debtorNames.get(1))
                                + " devono " + formattedAmount + "€ a " + debt.getCreatedBy();
                    } else {
                        StringBuilder namesBuilder = new StringBuilder();
                        for (int i = 0; i < debtorNames.size() - 1; i++) {
                            namesBuilder.append(capitalize(debtorNames.get(i))).append(", ");
                        }
                        namesBuilder.append("e ").append(capitalize(debtorNames.get(debtorNames.size() - 1)));
                        message = "💸 " + namesBuilder.toString() + " devono " + formattedAmount + "€ a " + debt.getCreatedBy();
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String name : debtorNames) {
                        double amt = debt.getParticipants().get(name);
                        sb.append("💸 ").append(capitalize(name)).append(" deve ")
                                .append(String.format("%.2f", amt)).append("€ a ").append(debt.getCreatedBy()).append("\n");
                    }
                    message = sb.toString().trim();
                }
                messages.add(message);
            }
            callback.accept(messages);
        });
    }

    private String capitalize(String str)
    {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

