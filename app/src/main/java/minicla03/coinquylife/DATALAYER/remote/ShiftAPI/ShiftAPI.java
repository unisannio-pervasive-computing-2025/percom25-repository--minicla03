package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;

import java.util.List;
import java.util.Map;

import minicla03.coinquylife.Shift.DOMAIN.model.HouseTask;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ShiftAPI {

    @Headers("Content-Type: application/json")
    @POST("Shift/rest/calendar/getAllShifts")
    Call<ResponseBody> retrieveAllShifts(@Body Map<String, String> body);

    @Headers("Content-Type: application/json")
    @POST("Shift/rest/calendar/getPlanning")
    Call<ResponseBody> getPlanning(@Body PlanningRequest body);

    @Headers("Content-Type: application/json")
    @PUT("Shift/rest/calendar/taskDone")
    Call<ResponseBody> taskDone(@Body Map<String, String> body);

    @Headers("Content-Type: application/json")
    @POST("Shift/rest/tasks/createTask")
    Call<ResponseBody> createTask(@Body HouseTask body);

    @Headers("Content-Type: application/json")
    @POST("Shift/rest/unAvailability/addAvailability")
    Call<ResponseBody> addUnAvailability(@Body UnavailabilityRequest body);

    @Headers("Content-Type: application/json")
    @POST("Shift/rest/unAvailability/initializeUnavailability")
    Call<ResponseBody> initializeUnavailability(@Body Map<String, List<Roommate>> body);

    @POST("Shift/rest/client/toRank")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> toRank(@Body Map<String, String> body);
}
