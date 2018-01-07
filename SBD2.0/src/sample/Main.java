package sample;

import java.math.BigDecimal;
import java.util.List;

import Model.Pojazd;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

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
            String sql = "select p from Pojazd p";
            //BigDecimal result = (BigDecimal)session.createNativeQuery(sql).getSingleResult();
            //System.out.println(result);
            Query<Pojazd>  query = session.createQuery(sql);
            List<Pojazd> pojazdy = query.getResultList();
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
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
