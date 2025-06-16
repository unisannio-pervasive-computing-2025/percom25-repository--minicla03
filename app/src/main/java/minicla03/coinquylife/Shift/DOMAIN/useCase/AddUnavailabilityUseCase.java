package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.UnavailabilityRequest;
import minicla03.coinquylife.Shift.DOMAIN.repository.IUnavailabilityRepository;

public class AddUnavailabilityUseCase
{
    private final IUnavailabilityRepository repo;

    public AddUnavailabilityUseCase(IUnavailabilityRepository repo) {
        this.repo = repo;
    }

    public void execute(String username, String houseId, String start, String end, Consumer<String> callback) throws ParseException
    {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        java.util.Date startDateTime = inputFormatter.parse(start);
        java.util.Date endDateTime = inputFormatter.parse(end);

        String formattedStart = outputFormatter.format(startDateTime);
        String formattedEnd = outputFormatter.format(endDateTime);

        UnavailabilityRequest req = new UnavailabilityRequest(
            username,
            houseId,
            formattedStart,
            formattedEnd
        );
        repo.addUnavailability(req, callback);
    }
}
