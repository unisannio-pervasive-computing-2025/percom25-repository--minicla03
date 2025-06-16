package minicla03.coinquylife.Rank.DOMAIN.UseCase;

import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;

public interface IRetriveClassificaUseCase
{
    void execute(Consumer<Map<String, Classifica>> callback);
}
