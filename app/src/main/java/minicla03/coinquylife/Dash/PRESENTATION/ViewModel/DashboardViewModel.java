package minicla03.coinquylife.Dash.PRESENTATION.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;

public class DashboardViewModel extends AndroidViewModel
{
    private final MutableLiveData<List<User>> listUser = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application)
    {
        super(application);
    }

    public LiveData<List<User>> getListUser()
    {
        return listUser;
    }

    public void setListUser(List<User> users)
    {
        listUser.setValue(users);
    }
}