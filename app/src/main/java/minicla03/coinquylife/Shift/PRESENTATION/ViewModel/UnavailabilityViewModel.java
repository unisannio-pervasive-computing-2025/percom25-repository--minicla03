package minicla03.coinquylife.Shift.PRESENTATION.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.UnavailabilityRepository;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;
import minicla03.coinquylife.Shift.DOMAIN.useCase.AddUnavailabilityUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.InitializeUnavailabilityUseCase;

public class UnavailabilityViewModel extends AndroidViewModel
{
    private final AddUnavailabilityUseCase addUseCase;
    private final InitializeUnavailabilityUseCase initUseCase;
    private final MutableLiveData<String> resultAdd = new MutableLiveData<>();
    private final MutableLiveData<String> resultInit = new MutableLiveData<>();

    public UnavailabilityViewModel(@NonNull Application app)
    {
        super(app);
        UnavailabilityRepository repo = new UnavailabilityRepository(app);
        addUseCase = new AddUnavailabilityUseCase(repo);
        initUseCase = new InitializeUnavailabilityUseCase(repo);
    }

    public void addUnavailability(String username, String houseId, String start, String end) throws ParseException
    {
        addUseCase.execute(username, houseId, start, end, resultAdd::postValue);
    }

    public void initializeRoommates(String houseId, List<User> roommates)
    {
        initUseCase.execute(houseId, roommates, resultInit::postValue);
    }

    public LiveData<String> getResultAdd() {
        return resultAdd;
    }

    public LiveData<String> getResultInit()
    {
        return resultInit;
    }
}
