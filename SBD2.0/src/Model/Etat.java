package Model;

import javax.persistence.*;

@Entity
@Table(name = "DZIALY")
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
}
