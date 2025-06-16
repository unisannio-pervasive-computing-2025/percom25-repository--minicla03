package minicla03.coinquylife.ProfileAndSetting.PRESENTATION.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.AuthRepository;
import minicla03.coinquylife.ProfileAndSetting.DOMAIN.useCase.IRetrieveUserByTokenUseCase;
import minicla03.coinquylife.ProfileAndSetting.DOMAIN.useCase.RetrieveUserByTokenUseCase;

public class ProfileViewModel extends AndroidViewModel
{
    private final IRetrieveUserByTokenUseCase retrieveUserByTokenUseCase;

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application)
    {
        super(application);
        IAuthRepository authRepository = new AuthRepository(application);
        this.retrieveUserByTokenUseCase = new RetrieveUserByTokenUseCase(authRepository);
    }

    public void loadUserProfile(String token, String houseId)
    {
        if (token == null || token.trim().isEmpty()) {
            userLiveData.postValue(null);
            return;
        }
        retrieveUserByTokenUseCase.execute(token, houseId, userLiveData::postValue);
    }

    public LiveData<User> getUserLiveData()
    {
        return userLiveData;
    }

}