package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;

import android.content.Context;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;

import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;

public class UnavailabilityRemoteSource
{
    private final ShiftAPI shiftAPI;

    public UnavailabilityRemoteSource(Context context)
    {
        shiftAPI = CoinquyLife.getApi().getApiShift(context);
    }

    public Call<ResponseBody> addUnavailability(UnavailabilityRequest request)
    {
        return shiftAPI.addUnAvailability(request);
    }

    public Call<ResponseBody> initializeUnavailability(String houseId,List<Roommate> roommates)
    {
        List<Roommate> roommatesUsername = roommates.stream()
                .map(r -> new Roommate(r.getUsernameRoommate(), houseId))
                .collect(Collectors.toList());
        return shiftAPI.initializeUnavailability(Map.of("coiquyList", roommates));
    }
}
