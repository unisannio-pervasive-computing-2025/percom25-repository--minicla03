package minicla03.coinquylife.Shift.PRESENTATION.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.Shift.DOMAIN.repository.IShiftRepository;
import minicla03.coinquylife.Shift.DOMAIN.useCase.GetPlanningUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.IGetPlanningUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.ITaskDoneUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.IToRankUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.TaskDoneUseCase;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.ShiftRepository;
import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.DOMAIN.useCase.ToRankUseCase;

public class ShiftViewModel extends AndroidViewModel
{
    private final IGetPlanningUseCase getPlanningUseCase;
    private final ITaskDoneUseCase taskDoneUseCase;
    private final IToRankUseCase toRankUseCase;

    private final MutableLiveData<List<CleaningAssignment>> planning = new MutableLiveData<>();
    private final MutableLiveData<String> taskDoneResult = new MutableLiveData<>();
    private final MutableLiveData<String> rankResult = new MutableLiveData<>();
    public MutableLiveData<String> problemId=new MutableLiveData<>();

    private final MutableLiveData<List<User>> listCoinquy = new MutableLiveData<>();
    private final MutableLiveData<String> houseId = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public ShiftViewModel(@NonNull Application application)
    {
        super(application);
        IShiftRepository repository = new ShiftRepository(application);
        getPlanningUseCase = new GetPlanningUseCase(repository);
        taskDoneUseCase = new TaskDoneUseCase(repository);
        toRankUseCase = new ToRankUseCase(repository);
    }

    public LiveData<List<CleaningAssignment>> getPlanning()
    {
        return planning;
    }

    public LiveData<String> getTaskDoneResult() {
        return taskDoneResult;
    }

    public MutableLiveData<String> getRankResult() {
        return rankResult;
    }

    public void confirmTaskCompletion(String token, String idShift, String typeTask, String username, String houseId, String endTime)
    {
        Log.d("ShiftViewModel", "Confirming task completion for idShift: " + idShift);
        taskDoneUseCase.execute(idShift, result -> {
            if (result != null) {
                taskDoneResult.postValue(result);
                toRankUseCase.execute(typeTask, username, houseId, endTime, resultRank -> {
                    if (resultRank != null) {
                        rankResult.postValue(result);
                    }
                });
            } else {
                errorResult.postValue("Error completing task");
            }
        }, errorResult::postValue);

    }

    public void fetchPlanning(String houseId)
    {
        isLoading.setValue(true);
        getPlanningUseCase.execute(houseId, this.getProblemId().getValue(),
                result -> {
                    if (result != null && !result.isEmpty()) {
                        problemId.setValue(result.get(0).getProblemId().toString());
                        planning.setValue(result);
                        isLoading.setValue(false);
                    }
                },
                error -> {
                    errorResult.postValue(error);
                    isLoading.setValue(false);
                });
    }

    public void retrieveAllShift(String houseId)
    {
        getPlanningUseCase.retriveAllShift(houseId,
                result->{
                    if(result!=null && !result.isEmpty()) {
                        String currentProblemId = getProblemId().getValue();
                        if (currentProblemId != null && !currentProblemId.isEmpty()) {
                            planning.setValue(result.stream()
                                    .filter(assignment -> assignment.getProblemId().equals(currentProblemId))
                                    .collect(Collectors.toList()));
                        }
                        planning.setValue(result.stream().filter(assignment -> assignment.getTask().isDone()==false).collect(Collectors.toList()));
                    }
                },
                errorResult::postValue);
    }



    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getProblemId() {
        return problemId;
    }

    public LiveData<List<User>> getListCoinquy() {
        return listCoinquy;
    }

    public void setListCoinquy(List<User> listCoinquy) {
        this.listCoinquy.setValue(listCoinquy);
    }

    public LiveData<String> getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId.setValue(houseId);
    }

    public LiveData<String> getErrorResult() {
        return errorResult;
    }

}
