package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.PlanningRequest;
import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.DOMAIN.repository.IShiftRepository;

public class GetPlanningUseCase implements IGetPlanningUseCase
{
    private final IShiftRepository repository;

    public GetPlanningUseCase(IShiftRepository repository) {
        this.repository = repository;
    }

    public void execute(String houseId, String problemId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error)
    {
        PlanningRequest request = new PlanningRequest(houseId, problemId);
        repository.getPlanning(request, callback, error);
    }

    public void retriveAllShift(String houseId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error)
    {
        repository.retrieveAllShift(houseId, callback, error);
    }
}
