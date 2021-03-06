package Model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIRMY")
public class Firma {
    @Id
    @Column(name = "NIP", nullable = false)
    private String nip;
    @Column(name = "ADRES", nullable = false)
    private String adres;
    @Column(name = "NAZWA", nullable = false)
    private String nazwa;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public SimpleStringProperty getNazwaProperty() {
        return new SimpleStringProperty(this.nazwa);
    }

    public SimpleStringProperty getAdresProperty() {
        return new SimpleStringProperty(this.adres);
    }
}
