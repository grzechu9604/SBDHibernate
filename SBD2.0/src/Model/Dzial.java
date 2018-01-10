package Model;

import DAOs.PracownikDAO;
import org.hibernate.SessionFactory;

import javax.persistence.*;

@Entity
@Table(name = "DZIALY", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID_SZEFA")
})
public class Dzial {
    @Id
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

    public Pracownik getSzef(SessionFactory factory){
        PracownikDAO pracownikDAO = new PracownikDAO();
        return pracownikDAO.getPracownikById(factory, this.idSzefa);
    }

}
