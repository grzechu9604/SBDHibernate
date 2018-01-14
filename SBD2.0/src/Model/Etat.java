package Model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ETATY")
public class Etat {
    @Id
    @Column(name = "NAZWA", nullable = false)
    private String nazwa;
    @Column(name = "STAWKA_GODZINOWA")
    private Double stawkaGodzinowa;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getStawkaGodzinowa() {
        return stawkaGodzinowa;
    }

    public void setStawkaGodzinowa(Double stawkaGodzinowa) {
        this.stawkaGodzinowa = stawkaGodzinowa;
    }

    public SimpleStringProperty getNazwaProperty() {
        return new SimpleStringProperty(this.nazwa);
    }

    public SimpleStringProperty getStawkaProperty() {
        return new SimpleStringProperty(this.stawkaGodzinowa.toString());
    }
}
