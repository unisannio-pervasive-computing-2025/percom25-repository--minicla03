package minicla03.coinquylife.Shift.PRESENTATION.UI;

import minicla03.coinquylife.Shift.DOMAIN.model.TimeSlot;

public class ShiftItem {

    private final String idShift;
    private final String coinquilino;
    private final String tipoCompito;
    private final String descrizione;
    private final TimeSlot orario;
    private boolean completed;

    public ShiftItem(String idShift, String coinquilino, String tipoCompito, String descrizione, TimeSlot orario, boolean completed) {
        this.idShift = idShift;
        this.coinquilino = coinquilino;
        this.tipoCompito = tipoCompito;
        this.descrizione = descrizione;
        this.orario = orario;
        this.completed = completed;
    }

    public String getCoinquilino() {
        return coinquilino;
    }

    public String getIdShift() {
        return idShift;
    }

    public String getTipoCompito() {
        return tipoCompito;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public TimeSlot getOrario() {
        return orario;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
