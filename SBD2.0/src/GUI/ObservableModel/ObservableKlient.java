package GUI.ObservableModel;

import javafx.beans.property.DoubleProperty;

public class ObservableKlient extends ObservableOsoba {
    private DoubleProperty rabat;

    public DoubleProperty getRabat() {
        return rabat;
    }

    public void setRabat(DoubleProperty rabat) {
        this.rabat = rabat;
    }
}
