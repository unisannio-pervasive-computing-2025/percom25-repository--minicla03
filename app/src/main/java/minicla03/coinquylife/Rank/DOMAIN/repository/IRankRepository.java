package minicla03.coinquylife.Rank.DOMAIN.repository;

import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.DashAPI.CoiquyListDTO;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;

public interface IRankRepository
{
    void getClassifica(CoiquyListDTO body, Consumer<Map<String, Classifica>> callback);
}
