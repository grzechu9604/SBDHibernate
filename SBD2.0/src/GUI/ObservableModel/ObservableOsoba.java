package GUI.ObservableModel;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableOsoba {
    private LongProperty id;
    private StringProperty imie;
    private StringProperty nazwisko;
    private StringProperty nr_telefonu;

    public LongProperty getId() {
        return id;
    }

    public void setId(LongProperty id) {
        this.id = id;
    }

    public StringProperty getImie() {
        return imie;
    }

    public void setImie(StringProperty imie) {
        this.imie = imie;
    }

    public StringProperty getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(SimpleStringProperty nazwisko) {
        this.nazwisko = nazwisko;
    }

    public StringProperty getNr_telefonu() {
        return nr_telefonu;
    }

    public void setNr_telefonu(StringProperty nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }
}
