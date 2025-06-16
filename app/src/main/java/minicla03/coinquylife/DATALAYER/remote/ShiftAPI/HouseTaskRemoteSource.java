package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;

import android.content.Context;

import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.Shift.DOMAIN.model.HouseTask;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class HouseTaskRemoteSource
{
    private final ShiftAPI apiService;

    public HouseTaskRemoteSource(Context context)
    {
        apiService= CoinquyLife.getApi().getApiShift(context);
    }

    public Call<ResponseBody> createTask(HouseTask task)
    {
        return apiService.createTask(task);
    }
}
