package sample;

import java.math.BigDecimal;
import java.util.List;

import DAOs.PracownikDAO;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();

        try{
            /*
            //String sql = "select p from Pojazd p";
            String sql = "select p from Pojazd p where p.nr_pojazdu = 1";
            Query query = session.createQuery(sql);
            List<Pojazd> pojazdy = query.getResultList();
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
            }

            sql = "select o from Osoba o";
            Query osobaQuery = session.createQuery(sql);
            List<Osoba> osobaList = osobaQuery.getResultList();
            for ( Osoba o : osobaList){
                System.out.println(o.getImie());
            }

            */
            PracownikDAO pracownikDAO = new PracownikDAO();
            Pracownik p = new Pracownik();
            p.setNazwa_etatu("SPAWACZ");
            p.setNr_dzialu(1L);
            p.setImie("Andrzej");
            p.setNazwisko("Janowski");
            p.setNr_telefonu("889789456");
            pracownikDAO.insert(factory, p);

            List<Pracownik> pracownikList = pracownikDAO.findAll(factory);
            for ( Pracownik x : pracownikList){
                System.out.println(x.getImie() + " " + x.getNazwisko());
            }
            /*
            sql = "select k from Klient k";
            Query klientQuery = session.createQuery(sql);
            List<Klient> klientList = klientQuery.getResultList();
            for ( Klient k : klientList){
                System.out.println(k.getRabat());
            }

            sql = "select k from Kategoria k";
            Query kategoriaQuery = session.createQuery(sql);
            List<Kategoria> kategoriaList = kategoriaQuery.getResultList();
            for ( Kategoria k : kategoriaList){
                System.out.println(k.getOpis());
            }

            sql = "select d from Dzial d";
            Query dzialQuery = session.createQuery(sql);
            List<Dzial> dzialList = dzialQuery.getResultList();
            for ( Dzial d : dzialList){
                System.out.println(d.getNazwa());
            }

            sql = "select z from Zlecenie z";
            Query zlecenieQuery = session.createQuery(sql);
            List<Zlecenie> zlecenieList = zlecenieQuery.getResultList();
            for ( Zlecenie z : zlecenieList){
                System.out.println(z.getDataWplywu());
            }

            sql = "select c from Czesc c";
            Query czescQuery = session.createQuery(sql);
            List<Czesc> czescList = czescQuery.getResultList();
            for ( Czesc c : czescList){
                System.out.println(c.getCenaZakupu());
            }

            sql = "select f from Firma f";
            Query firmaQuery = session.createQuery(sql);
            List<Firma> firmaList = firmaQuery.getResultList();
            for ( Firma f : firmaList){
                System.out.println(f.getNazwa());
            }

            sql = "select e from Etat e";
            Query etatQuery = session.createQuery(sql);
            List<Etat> etatList = etatQuery.getResultList();
            for (Etat e : etatList){
                System.out.println(e.getStawkaGodzinowa());
            }
            */

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
