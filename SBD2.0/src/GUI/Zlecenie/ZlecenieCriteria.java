package GUI.Zlecenie;

import Model.Dzial;
import Model.Pojazd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ZlecenieCriteria {
    private List<Pojazd> pojazdy = new LinkedList<>();
    private Date dataWplywuOd;
    private Date dataWplywuDo;
    private Date dataZakOd;
    private Date dataZakDo;
    private Date dataOdbOd;
    private Date dataOdbDo;
    private List<Dzial> dzialy = new LinkedList<>();
    private Double cenaOd;
    private Double cenaDo;

    public List<Pojazd> getPojazdy() {
        return pojazdy;
    }

    public void setPojazdy(List<Pojazd> pojazdy) {
        this.pojazdy = pojazdy;
    }

    public Date getDataWplywuOd() {
        return dataWplywuOd;
    }

    public void setDataWplywuOd(Date dataWplywuOd) {
        this.dataWplywuOd = dataWplywuOd;
    }

    public Date getDataWplywuDo() {
        return dataWplywuDo;
    }

    public void setDataWplywuDo(Date dataWplywuDo) {
        this.dataWplywuDo = dataWplywuDo;
    }

    public Date getDataZakOd() {
        return dataZakOd;
    }

    public void setDataZakOd(Date dataZakOd) {
        this.dataZakOd = dataZakOd;
    }

    public Date getDataZakDo() {
        return dataZakDo;
    }

    public void setDataZakDo(Date dataZakDo) {
        this.dataZakDo = dataZakDo;
    }

    public Date getDataOdbOd() {
        return dataOdbOd;
    }

    public void setDataOdbOd(Date dataOdbOd) {
        this.dataOdbOd = dataOdbOd;
    }

    public Date getDataOdbDo() {
        return dataOdbDo;
    }

    public void setDataOdbDo(Date dataOdbDo) {
        this.dataOdbDo = dataOdbDo;
    }

    public List<Dzial> getDzialy() {
        return dzialy;
    }

    public void setDzialy(List<Dzial> dzialy) {
        this.dzialy = dzialy;
    }

    public Double getCenaOd() {
        return cenaOd;
    }

    public void setCenaOd(Double cenaOd) {
        this.cenaOd = cenaOd;
    }

    public Double getCenaDo() {
        return cenaDo;
    }

    public void setCenaDo(Double cenaDo) {
        this.cenaDo = cenaDo;
    }
}
