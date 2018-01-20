package Model;

import DAOs.PracownikDAO;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

import javax.persistence.*;

@Entity
@Table(name = "DZIALY", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_SZEFA")
})
public class Dzial {
    @Id
    @SequenceGenerator(name = "DZIALY_SEQ", sequenceName = "DZIALY_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DZIALY_SEQ")
    @Column(name = "NR_DZIALU", nullable = false)
    private Long id;
    @Column(name = "NAZWA", nullable = false)
    private String nazwa;
    @Column(name = "ID_SZEFA")
    private Long idSzefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getIdSzefa() {
        return idSzefa;
    }

    public void setIdSzefa(Long idSzefa) {
        this.idSzefa = idSzefa;
    }

    public SimpleStringProperty getNazwaProperty() {
        return new SimpleStringProperty(this.nazwa);
    }

    public Pracownik getSzef(SessionFactory factory) throws DatabaseException {
        PracownikDAO pracownikDAO = new PracownikDAO(factory);
        return pracownikDAO.getPracownikById(this.idSzefa);
    }

    @Override
    public String toString() {
        return this.nazwa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Dzial))
            return false;

        Dzial dzial = (Dzial) o;

        return dzial.getId().equals(this.id);
    }
}
