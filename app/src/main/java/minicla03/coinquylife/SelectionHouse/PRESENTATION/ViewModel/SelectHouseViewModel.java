package minicla03.coinquylife.SelectionHouse.PRESENTATION.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import minicla03.coinquylife.DATALAYER.RepositoryEntity.HouseSelectionRepository;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;
import minicla03.coinquylife.SelectionHouse.DOMAIN.Repository.ISelectHouseRepository;
import minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase.IJoinHouseUseCase;
import minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase.INewHouseUseCase;
import minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase.JoinHouseUseCase;
import minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase.NewHouseUseCase;

public class SelectHouseViewModel extends AndroidViewModel
{
    private final INewHouseUseCase newHouseUseCase;
    private final IJoinHouseUseCase joinHouseUseCase;

    private final MutableLiveData<SelectHouseResult> houseCreationResult = new MutableLiveData<>();
    private final MutableLiveData<SelectHouseResult> joinHouseResult = new MutableLiveData<>();
    private final MutableLiveData<String> houseID=new MutableLiveData<>();

    public SelectHouseViewModel(@NonNull Application application)
    {
        super(application);
        ISelectHouseRepository repo = new HouseSelectionRepository(application);
        this.newHouseUseCase = new NewHouseUseCase(repo);
        this.joinHouseUseCase = new JoinHouseUseCase(repo);
    }

    public void createHouse(String houseName, String address)
    {
        newHouseUseCase.execute(houseName, address, result -> {
            houseID.postValue(result.getCode());
            houseCreationResult.postValue(result);
        });
    }

    public void joinHouse(String houseId)
    {
        joinHouseUseCase.execute(houseId, joinHouseResult::postValue);
    }


    public LiveData<SelectHouseResult> getHouseCreationResult()
    {
        return houseCreationResult;
    }


    public LiveData<SelectHouseResult> getJoinHouseResult()
    {
        return joinHouseResult;
    }

    public LiveData<String> getHouseCode()
    {
        return houseID;
    }

}
