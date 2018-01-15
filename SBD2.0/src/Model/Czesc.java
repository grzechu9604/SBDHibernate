package Model;

import DAOs.KategoriaDAO;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

@Entity
@Table(name = "Czesci")
public class Czesc {
    @Id
    @SequenceGenerator(name = "CZESCI_SEQ", sequenceName = "CZESCI_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CZESCI_SEQ")
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

    public Kategoria getKategoria(KategoriaDAO kategoriaDAO) {
        return kategoriaDAO.getKategoriaByNazwa(this.nazwaKategorii);
    }

    public SimpleStringProperty getCenaZakupuProperty() {
        return new SimpleStringProperty(this.cenaZakupu.toString());
    }
}
