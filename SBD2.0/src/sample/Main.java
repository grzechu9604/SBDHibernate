package sample;

import java.math.BigDecimal;
import java.util.List;

import DAOs.*;
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
            CzescDAO czescDAO = new CzescDAO();
            List<Czesc> czescList = czescDAO.findAll(factory);
            for ( Czesc c : czescList){
                System.out.println(c.getCenaZakupu());
            }

            DzialDAO dzialDAO = new DzialDAO();
            List<Dzial> dzialList = dzialDAO.findAll(factory);
            for ( Dzial d : dzialList){
                System.out.println(d.getNazwa());
            }

            EtatDAO etatDAO = new EtatDAO();
            List<Etat> etatList = etatDAO.findAll(factory);
            for (Etat e : etatList){
                System.out.println(e.getStawkaGodzinowa());
            }

            FirmaDAO firmaDAO = new FirmaDAO();
            List<Firma> firmaList = firmaDAO.findAll(factory);
            for ( Firma f : firmaList){
                System.out.println(f.getNazwa());
            }

            KategoriaDAO kategoriaDAO = new KategoriaDAO();
            List<Kategoria> kategoriaList = kategoriaDAO.findAll(factory);
            for ( Kategoria k : kategoriaList){
                System.out.println(k.getOpis());
            }

            KlientDAO klientDAO = new KlientDAO();
            List<Klient> klientList = klientDAO.findAll(factory);
            for ( Klient k : klientList){
                System.out.println(k.getRabat());
            }

            OsobaDAO osobaDAO = new OsobaDAO();
            List<Osoba> osobaList = osobaDAO.findAll(factory);
            for ( Osoba o : osobaList){
                System.out.println(o.getImie());
            }

            PojazdDAO pojazdDAO = new PojazdDAO();
            List<Pojazd> pojazdy = pojazdDAO.findAll(factory);
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
            }

            PracownikDAO pracownikDAO = new PracownikDAO();
            List<Pracownik> pracownikList = pracownikDAO.findAll(factory);
            for ( Pracownik x : pracownikList){
                System.out.println(x.getImie() + " " + x.getNazwisko());
            }

            ZlecenieDAO zlecenieDAO = new ZlecenieDAO();
            List<Zlecenie> zlecenieList = zlecenieDAO.findAll(factory);
            for ( Zlecenie z : zlecenieList){
                System.out.println(z.getDataWplywu());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
