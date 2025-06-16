package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import minicla03.coinquylife.CoinquyLife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShiftRemoteSource
{
    private final ShiftAPI apiService;

    public ShiftRemoteSource(Context context) {
        apiService = CoinquyLife.getApi().getApiShift(context);
    }

    public Call<ResponseBody> getPlanning(PlanningRequest request)
    {
        return apiService.getPlanning(request);
    }

    public Call<ResponseBody> taskDone(String id) {
        Map<String, String> body = new HashMap<>();
        body.put("id", id);
        return apiService.taskDone(body);
    }

    public Call<ResponseBody> toRank(String typeTask, String username, String houseId, String dateComplete, String endTime) {
        Map<String, String> body = new HashMap<>();
        body.put("typeTask", typeTask);
        body.put("username", username);
        body.put("houseId", houseId);
        body.put("dateComplete", dateComplete);
        body.put("endTime", endTime);
        return apiService.toRank(body);
    }

    public Call<ResponseBody> retrieveAllShift(String houseId)
    {
        return apiService.retrieveAllShifts(Map.of("houseId", houseId));
    }
}
