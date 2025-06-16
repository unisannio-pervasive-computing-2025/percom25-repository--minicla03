package minicla03.coinquylife.Shift.DOMAIN.repository;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.PlanningRequest;
import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;

public interface IShiftRepository
{
    void getPlanning(PlanningRequest request, Consumer<List<CleaningAssignment>> callback, Consumer<String> error);

    void taskDone(String id, Consumer<String> onSuccess, Consumer<String> onError);

    void toRank(String typeTask, String username, String houseId, String dateComplete, String endTime, Consumer<String> callback, Consumer<String> error);

    void retrieveAllShift(String houseId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error);
}
