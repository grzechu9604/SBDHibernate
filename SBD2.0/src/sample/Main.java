package sample;

import java.math.BigDecimal;
import java.util.List;

import Model.Klient;
import Model.Osoba;
import Model.Pojazd;
import Model.Pracownik;
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

        Session session = factory.getCurrentSession();
        try{
            session.getTransaction().begin();
            //String sql = "select p from Pojazd p";
            String sql = "select p from Pojazd p where p.nr_pojazdu = 1";
            //BigDecimal result = (BigDecimal)session.createNativeQuery(sql).getSingleResult();
            //System.out.println(result);
            Query<Pojazd>  query = session.createQuery(sql);
            List<Pojazd> pojazdy = query.getResultList();
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
            }

            sql = "select o from Osoba o";
            Query<Osoba> osobaQuery = session.createQuery(sql);
            List<Osoba> osobaList = osobaQuery.getResultList();
            for ( Osoba o : osobaList){
                System.out.println(o.getImie());
            }

            sql = "select p from Pracownik p";
            Query<Pracownik> pracownikQuery = session.createQuery(sql);
            List<Pracownik> pracownikList = pracownikQuery.getResultList();
            for ( Pracownik p : pracownikList){
                System.out.println(p.getNr_dzialu());
            }

            sql = "select k from Klient k";
            Query<Klient> klientQuery = session.createQuery(sql);
            List<Klient> klientList = klientQuery.getResultList();
            for ( Klient k : klientList){
                System.out.println(k.getRabat());
            }

            session.getTransaction().commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
    }
}
