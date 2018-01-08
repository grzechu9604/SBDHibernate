package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Czesci")
public class Czesc {
    @Id
    @Column(name = "NR_CZESCI", nullable = false)
    private Long id;
    @Column(name = "CENA_ZAKUPU")
    private Double cenaZakupu;
    @Column(name = "CENA_SPRZEDAZY")
    private Double cenaSprzedazy;
    @Column(name = "KATEGORIE_NAZWA", nullable = false)
    private String nazwaKategorii;
    @Column(name = "ZLECENIE_NR_NAPRAWY")
    private String zlecenieNrNaprawy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCenaZakupu() {
        return cenaZakupu;
    }

    public void setCenaZakupu(Double cenaZakupu) {
        this.cenaZakupu = cenaZakupu;
    }

    public Double getCenaSprzedazy() {
        return cenaSprzedazy;
    }

    public void setCenaSprzedazy(Double cenaSprzedazy) {
        this.cenaSprzedazy = cenaSprzedazy;
    }

    public String getNazwaKategorii() {
        return nazwaKategorii;
    }

    public void setNazwaKategorii(String nazwaKategorii) {
        this.nazwaKategorii = nazwaKategorii;
    }

    public String getZlecenieNrNaprawy() {
        return zlecenieNrNaprawy;
    }

    public void setZlecenieNrNaprawy(String zlecenieNrNaprawy) {
        this.zlecenieNrNaprawy = zlecenieNrNaprawy;
    }
}
