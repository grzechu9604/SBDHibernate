package Model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;

@Entity
@Table(name = "ZLECENIA")
public class Zlecenie {
    @Id
    @SequenceGenerator(name = "ZLECENIA_SEQ", sequenceName = "ZLECENIA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ZLECENIA_SEQ")
    @Column(name = "NR_NAPRAWY", nullable = false)
    private Long id;
    @Column(name = "DATA_WPLYWU", nullable = false)
    private Date dataWplywu;
    @Column(name = "PLANOWANE_ZAKONCZENIE", nullable = false)
    private Date planowaneZakonczenie;
    @Column(name = "DATA_ODBIORU")
    private Date dataOdbioru;
    @Column(name = "CENA")
    private Double cena;
    @Column(name = "POJAZDY_NR_POJAZDU", nullable = false)
    private Long idPojazdu;

    @Transient
    private LinkedList<Dzial> connectorsToDzial;
    @Transient
    private LinkedList<Czesc> uzyteCzesci;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataWplywu() {
        return dataWplywu;
    }

    public void setDataWplywu(Date dataWplywu) {
        this.dataWplywu = dataWplywu;
    }

    public Date getPlanowaneZakonczenie() {
        return planowaneZakonczenie;
    }

    public void setPlanowaneZakonczenie(Date planowaneZakonczenie) {
        this.planowaneZakonczenie = planowaneZakonczenie;
    }

    public Date getDataOdbioru() {
        return dataOdbioru;
    }

    public void setDataOdbioru(Date dataOdbioru) {
        this.dataOdbioru = dataOdbioru;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Long getIdPojazdu() {
        return idPojazdu;
    }

    public void setIdPojazdu(Long idPojazdu) {
        this.idPojazdu = idPojazdu;
    }

    public SimpleStringProperty getDataWplywyProperty() {
        return new SimpleStringProperty(this.dataWplywu.toString());
    }

    public LinkedList<Dzial> getConnectorsToDzial() {
        return connectorsToDzial;
    }

    public void setConnectorsToDzial(LinkedList<Dzial> connectorsToDzial) {
        this.connectorsToDzial = connectorsToDzial;
    }

    public LinkedList<Czesc> getUzyteCzesci() {
        return uzyteCzesci;
    }

    public void setUzyteCzesci(LinkedList<Czesc> uzyteCzesci) {
        this.uzyteCzesci = uzyteCzesci;
    }
}
