package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ZLECENIA_DZIALOW")
public class ZlecenieDzialConnector implements Serializable {
    @Id
    @Column(name = "NR_DZIALU", nullable = false)
    private Long idDzialu;
    @Id
    @Column(name = "NR_NAPRAWY", nullable = false)
    private Long idZlecenia;

    public ZlecenieDzialConnector() {
    }

    public ZlecenieDzialConnector(Zlecenie z, Dzial d) {
        this.idZlecenia = z.getId();
        this.idDzialu = d.getId();
    }

    @Override
    public int hashCode() {
        return 17 + this.idDzialu.intValue() * 31 + this.idZlecenia.intValue() * 31;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof ZlecenieDzialConnector))
            return false;

        ZlecenieDzialConnector zdc = (ZlecenieDzialConnector) o;

        return zdc.getIdDzialu().equals(this.idDzialu) && zdc.getIdZlecenia().equals(this.idZlecenia);
    }

    public Long getIdDzialu() {
        return idDzialu;
    }

    public void setIdDzialu(Long idDzialu) {
        this.idDzialu = idDzialu;
    }

    public Long getIdZlecenia() {
        return idZlecenia;
    }

    public void setIdZlecenia(Long idZlecenia) {
        this.idZlecenia = idZlecenia;
    }
}
