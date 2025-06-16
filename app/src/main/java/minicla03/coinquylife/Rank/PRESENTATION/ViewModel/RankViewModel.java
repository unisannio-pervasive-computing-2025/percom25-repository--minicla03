package minicla03.coinquylife.Rank.PRESENTATION.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Map;

import minicla03.coinquylife.DATALAYER.RepositoryEntity.RankRepository;
import minicla03.coinquylife.Rank.DOMAIN.UseCase.IRetriveClassificaUseCase;
import minicla03.coinquylife.Rank.DOMAIN.UseCase.RetriveClassificaUseCase;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import minicla03.coinquylife.Rank.DOMAIN.repository.IRankRepository;

public class RankViewModel extends AndroidViewModel
{
    private final IRetriveClassificaUseCase retriveClassificaUseCase;

    private final MutableLiveData<Map<String, Classifica>> classificaLiveData = new MutableLiveData<>();

    public RankViewModel(@NonNull Application application)
    {
        super(application);
        IRankRepository repo= new RankRepository(application);
        retriveClassificaUseCase = new RetriveClassificaUseCase(repo, application);
    }

    public MutableLiveData<Map<String, Classifica>> getClassificaLiveData()
    {
        return classificaLiveData;
    }

    public void fetchClassifica()
    {
        retriveClassificaUseCase.execute(classificaLiveData::postValue);
    }

}
