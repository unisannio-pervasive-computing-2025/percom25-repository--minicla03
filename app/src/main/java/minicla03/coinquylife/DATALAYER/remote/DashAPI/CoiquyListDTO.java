package minicla03.coinquylife.DATALAYER.remote.DashAPI;

import java.util.List;

public class CoiquyListDTO
{
    private List<CoiquyDTO> coiquyList;

    public List<CoiquyDTO> getCoiquyList() {
        return coiquyList;
    }

    public void setCoiquyList(List<CoiquyDTO> coiquyList) {
        this.coiquyList = coiquyList;
    }

    @Override
    public String toString() {
        return "CoiquyListDTO{" +
                "coiquyList=" + coiquyList +
                '}';
    }
}
