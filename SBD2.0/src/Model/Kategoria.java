package Model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Kategorie")
public class Kategoria {
    @Id
    @Column(name = "NAZWA", nullable = false)
    private String nazwa;
    @Column(name = "OPIS", nullable = false)
    private String opis;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public SimpleStringProperty getNazwaProperty() {
        return new SimpleStringProperty(this.nazwa);
    }
}
