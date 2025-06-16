package minicla03.coinquylife.Shift.DOMAIN.repository;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.UnavailabilityRequest;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;

public interface IUnavailabilityRepository
{
    void addUnavailability(UnavailabilityRequest request, Consumer<String> callback);

    void initializeUnavailability(String houseId, List<Roommate> roommates, Consumer<String> callback);
}
