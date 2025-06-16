package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;

public interface IGetPlanningUseCase
{
    void execute(String houseId, String problemId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error);

    void retriveAllShift(String houseId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error);
}
