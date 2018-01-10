package sample;

import DAOs.*;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.util.List;

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
            CzescDAO czescDAO = new CzescDAO(factory);
            List<Czesc> czescList = czescDAO.findAll();
            for ( Czesc c : czescList){
                System.out.println(c.getCenaZakupu());
            }

            DzialDAO dzialDAO = new DzialDAO(factory);
            List<Dzial> dzialList = dzialDAO.findAll();
            for ( Dzial d : dzialList){
                System.out.println(d.getNazwa());
                System.out.println(d.getSzef(factory).getImie());
            }

            EtatDAO etatDAO = new EtatDAO(factory);
            List<Etat> etatList = etatDAO.findAll();
            for (Etat e : etatList){
                System.out.println(e.getStawkaGodzinowa());
            }

            FirmaDAO firmaDAO = new FirmaDAO(factory);
            List<Firma> firmaList = firmaDAO.findAll();
            for ( Firma f : firmaList){
                System.out.println(f.getNazwa());
            }

            KategoriaDAO kategoriaDAO = new KategoriaDAO(factory);
            List<Kategoria> kategoriaList = kategoriaDAO.findAll();
            for ( Kategoria k : kategoriaList){
                System.out.println(k.getOpis());
            }

            KlientDAO klientDAO = new KlientDAO(factory);
            List<Klient> klientList = klientDAO.findAll();
            for ( Klient k : klientList){
                System.out.println(k.getRabat());
            }

            OsobaDAO osobaDAO = new OsobaDAO(factory);
            List<Osoba> osobaList = osobaDAO.findAll();
            for ( Osoba o : osobaList){
                System.out.println(o.getImie());
            }

            PojazdDAO pojazdDAO = new PojazdDAO(factory);
            List<Pojazd> pojazdy = pojazdDAO.findAll();
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
            }

            PracownikDAO pracownikDAO = new PracownikDAO(factory);
            List<Pracownik> pracownikList = pracownikDAO.findAll();
            for ( Pracownik x : pracownikList){
                System.out.println(x.getImie() + " " + x.getNazwisko());
            }

            ZlecenieDAO zlecenieDAO = new ZlecenieDAO(factory);
            List<Zlecenie> zlecenieList = zlecenieDAO.findAll();
            for ( Zlecenie z : zlecenieList){
                System.out.println(z.getDataWplywu());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            factory.close();
        }
    }
}
