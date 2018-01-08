package Model;

import javax.persistence.*;

@Entity
@Table(name = "OSOBY")
@Inheritance(strategy=InheritanceType.JOINED)
public class Osoba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "IMIE", nullable = false)
    private String imie;
    @Column(name = "NAZWISKO", nullable = false)
    private String nazwisko;
    @Column(name = "NR_TELEFONU", nullable = false)
    private String nr_telefonu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }
}