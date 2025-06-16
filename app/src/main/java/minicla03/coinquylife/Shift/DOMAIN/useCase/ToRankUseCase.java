package minicla03.coinquylife.Shift.DOMAIN.useCase;

import android.os.Build;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.RepositoryEntity.ShiftRepository;
import minicla03.coinquylife.Shift.DOMAIN.repository.IShiftRepository;

public class ToRankUseCase implements IToRankUseCase
{
    private final IShiftRepository repository;

    public ToRankUseCase(IShiftRepository repository)
    {
        this.repository = repository;
    }

    public void execute(String typeTask, String username, String houseId, String endTime,
                        Consumer<String> callback)
    {
        LocalDateTime dateComplete = null;
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateComplete = LocalDateTime.now();
        }
        Log.d("ToRankUseCase", "tok rank repo");
        repository.toRank(typeTask, username, houseId, dateComplete.toString(), endTime, callback, error -> callback.accept(null));
    }
}
