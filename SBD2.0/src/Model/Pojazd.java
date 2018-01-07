package Model;

import javax.persistence.*;

@Entity
@Table(name = "POJAZDY", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NR_POJAZDU")
})
public class Pojazd {
    @Id
    @Column(name = "NR_POJAZDU", nullable = false)
    private Long nr_pojazdu;

    @Column(name = "MARKA", nullable = false)
    private String marka;

    @Column(name = "KLIENCI_ID")
    private Long id_klienta;

    @Column(name = "FIRMY_NIP")
    private String nip_firmy;

    @Column(name = "NR_REJESTRACYJNY")
    private String nr_rejestracyjny;

    public Pojazd(){}

    public long getNr_pojazdu() {
        return nr_pojazdu;
    }

    public void setNr_pojazdu(Long nr_pojazdu) {
        this.nr_pojazdu = nr_pojazdu;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public long getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(Long id_klienta) {
        this.id_klienta = id_klienta;
    }

    public String getNip_firmy() {
        return nip_firmy;
    }

    public void setNip_firmy(String nip_firmy) {
        this.nip_firmy = nip_firmy;
    }

    public String getNr_rejestracyjny() {
        return nr_rejestracyjny;
    }

    public void setNr_rejestracyjny(String nr_rejestracyjny) {
        this.nr_rejestracyjny = nr_rejestracyjny;
    }
}
