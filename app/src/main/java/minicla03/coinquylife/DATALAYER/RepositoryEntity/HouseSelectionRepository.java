package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;

import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.HouseSelectionRemoteDataSource;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;
import minicla03.coinquylife.SelectionHouse.DOMAIN.Repository.ISelectHouseRepository;
import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;

public class HouseSelectionRepository implements ISelectHouseRepository
{
    private final HouseSelectionRemoteDataSource remoteDataSource;

    public HouseSelectionRepository(Context context)
    {
        remoteDataSource = new HouseSelectionRemoteDataSource(context);
    }

    public Call<SelectHouseResult> createHouseRemote(CoinquyHouse house)
    {
        return remoteDataSource.createHouse(house);
    }

    public Call<SelectHouseResult> joinHouseRemote(CoinquyHouse house)
    {
        return remoteDataSource.loginHouse(house);
    }

}
