package Model;

import javafx.beans.property.SimpleDoubleProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Klienci")
public class Klient extends Osoba {
    @Column(name = "RABAT")
    private Double rabat;

    public SimpleDoubleProperty getRabatProperty() {
        return new SimpleDoubleProperty(this.rabat);
    }

    public Double getRabat() {
        return rabat;
    }

    public void setRabat(Double rabat) {
        this.rabat = rabat;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Klient))
            return false;

        Klient klient = (Klient) o;

        return klient.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
