package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Pracownicy")
public class Pracownik extends Osoba {
    @Column(name = "NR_DZIALU", nullable = false)
    private Long nr_dzialu;
    @Column(name = "nazwa_etatu", nullable = false)
    private String nazwa_etatu;

    public Long getNr_dzialu() {
        return nr_dzialu;
    }

    public void setNr_dzialu(Long nr_dzialu) {
        this.nr_dzialu = nr_dzialu;
    }

    public String getNazwa_etatu() {
        return nazwa_etatu;
    }

    public void setNazwa_etatu(String nazwa_etatu) {
        this.nazwa_etatu = nazwa_etatu;
    }
}
