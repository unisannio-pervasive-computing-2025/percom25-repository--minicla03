package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.UnavailabilityRepository;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;

public class InitializeUnavailabilityUseCase
{
    private final UnavailabilityRepository repo;

    public InitializeUnavailabilityUseCase(UnavailabilityRepository repo) {
        this.repo = repo;
    }

    public void execute(String houseId, List<User> roommatesU, Consumer<String> callback)
    {
        List<Roommate> roommates= roommatesU.stream()
            .map(user -> new Roommate(user.getUsername(), houseId))
            .collect(Collectors.toList());
        repo.initializeUnavailability(houseId, roommates, callback);
    }
}
