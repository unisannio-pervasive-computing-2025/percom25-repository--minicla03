package minicla03.coinquylife.Rank.DOMAIN.UseCase;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.remote.DashAPI.CoiquyDTO;
import minicla03.coinquylife.DATALAYER.remote.DashAPI.CoiquyListDTO;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import minicla03.coinquylife.Rank.DOMAIN.repository.IRankRepository;

public class RetriveClassificaUseCase implements IRetriveClassificaUseCase
{
    private final Application application;
    private IRankRepository repo;
    private final Gson gson = new Gson();

    public RetriveClassificaUseCase(IRankRepository repo, Application application)
    {
        this.repo = repo;
        this.application = application;
    }

    public void execute(Consumer<Map<String, Classifica>> callback)
    {
        String json = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("coinquyList", null);
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> listCoinquy = gson.fromJson(json, type);
        String houseId = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("houseCode", null);
        System.out.println(houseId);
        List<CoiquyDTO> coinquyList = listCoinquy.stream()
                .map(user ->
                    new CoiquyDTO(user.getUsername(), houseId)
                )
                .collect(Collectors.toList());

        CoiquyListDTO body = new CoiquyListDTO();
        body.setCoiquyList(coinquyList);
        repo.getClassifica(body, callback);
    }
}
