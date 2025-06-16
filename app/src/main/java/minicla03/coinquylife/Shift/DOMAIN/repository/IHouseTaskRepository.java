package minicla03.coinquylife.Shift.DOMAIN.repository;

import java.util.function.Consumer;

import minicla03.coinquylife.Shift.DOMAIN.model.HouseTask;

public interface IHouseTaskRepository
{
    void createTask(HouseTask houseTask, Consumer<String> callback);
}
