package DAOs;

import GUI.Zlecenie.ZlecenieCriteria;
import Model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.DatabaseException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ZlecenieDAO extends AbstractDAO<Zlecenie> {
    public ZlecenieDAO(SessionFactory factory) {
        super(factory, new Zlecenie());
    }


    @Override
    public boolean insert(Zlecenie element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.save(element);

            element.getUzyteCzesci().forEach(c -> c.setZlecenieNrNaprawy(element.getId().toString()));
            element.getUzyteCzesci().forEach(c -> session.update(c));

            LinkedList<ZlecenieDzialConnector> zdcs = new LinkedList<>();
            element.getConnectorsToDzial().forEach(cd -> zdcs.add(new ZlecenieDzialConnector(element, cd)));
            zdcs.forEach(zdc -> session.save(zdc));

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Zlecenie element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.update(element);

            LinkedList<Czesc> czescsConnectedBefore = this.GetAssignedCzescToZlecenie(element.getId());
            LinkedList<ZlecenieDzialConnector> connsctorsBefore = this.getZlecenieDzialConnectorForZlecenieId(element.getId());

            czescsConnectedBefore.removeAll(element.getUzyteCzesci());
            czescsConnectedBefore.forEach(c -> c.setZlecenieNrNaprawy(null));

            czescsConnectedBefore.forEach(c -> session.update(c));
            element.getUzyteCzesci().forEach(c -> c.setZlecenieNrNaprawy(element.getId().toString()));
            element.getUzyteCzesci().forEach(c -> session.update(c));

            LinkedList<ZlecenieDzialConnector> newConnectors = new LinkedList<>();
            element.getConnectorsToDzial().forEach(cd -> newConnectors.add(new ZlecenieDzialConnector(element, cd)));

            LinkedList<ZlecenieDzialConnector> connectorsToDelete = new LinkedList<>();
            connectorsToDelete.addAll(connsctorsBefore);
            connectorsToDelete.removeAll(newConnectors);

            LinkedList<ZlecenieDzialConnector> connectorsToAdd = new LinkedList<>();
            connectorsToAdd.addAll(newConnectors);
            connectorsToAdd.removeAll(connsctorsBefore);

            connectorsToDelete.forEach(c -> session.delete(c));
            connectorsToAdd.forEach(c -> session.save(c));

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    private LinkedList<Czesc> GetAssignedCzescToZlecenie(Long zlecenieId) throws DatabaseException {
        LinkedList<Czesc> results = new LinkedList<>();

        Query<Object> query = this.factory.getCurrentSession().createQuery("select c from Czesc as c " +
                " where c.zlecenieNrNaprawy = :id ");
        query.setParameter("id", zlecenieId.toString());

        for (Object result : query.list()) {
            Czesc c = (Czesc) result;
            results.add(c);
        }
        return results;
    }

    private LinkedList<ZlecenieDzialConnector> getZlecenieDzialConnectorForZlecenieId(Long zlecenieId) {
        LinkedList<ZlecenieDzialConnector> results = new LinkedList<>();

        Query<ZlecenieDzialConnector> query = this.factory.getCurrentSession().createQuery("select z from ZlecenieDzialConnector as z " +
                " where z.idZlecenia = :id ");
        query.setParameter("id", zlecenieId);

        for (Object result : query.list()) {
            ZlecenieDzialConnector zdc = (ZlecenieDzialConnector) result;
            results.add(zdc);
        }
        return results;
    }

    public List<Zlecenie> getFilteredZlecenie(ZlecenieCriteria zc) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        List<Zlecenie> results = new LinkedList<>();

        try {
            String sqlQuery = generateSqlQuery(zc);
            Query<Object[]> query = session.createNativeQuery(sqlQuery);
            query = setParameters(query, zc);

            for (Object[] o : query.list()) {
                results.add(castToZlecenie(o));
            }

            session.getTransaction().commit();

            return results;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się pobrać listy z bazy danych.");
        } finally {
            session.close();
        }
    }

    private String generateSqlQuery(ZlecenieCriteria zc) {
        String sqlQuery = "select * from ZLECENIA z where 1 = 1 ";

        if (!zc.getDzialy().isEmpty())
            sqlQuery += " and z.NR_NAPRAWY in (select NR_NAPRAWY from ZLECENIA_DZIALOW where NR_DZIALU in ( :NR_DZIALU )) ";

        if (!zc.getPojazdy().isEmpty())
            sqlQuery += " and z.POJAZDY_NR_POJAZDU in ( :POJAZDY_NR_POJAZDU ) ";

        if (zc.getCenaDo() != null)
            sqlQuery += " and z.cena <= :cenaDo ";

        if (zc.getCenaOd() != null)
            sqlQuery += " and z.cena >= :cenaOd ";

        if (zc.getDataOdbDo() != null)
            sqlQuery += " and z.DATA_ODBIORU <= :DATA_ODBIORU_DO ";

        if (zc.getDataOdbOd() != null)
            sqlQuery += " and z.DATA_ODBIORU >= :DATA_ODBIORU_OD ";

        if (zc.getDataWplywuDo() != null)
            sqlQuery += " and z.DATA_WPLYWU <= :DATA_WPLYWU_DO ";

        if (zc.getDataWplywuOd() != null)
            sqlQuery += " and z.DATA_WPLYWU >= :DATA_WPLYWU_OD ";

        if (zc.getDataZakDo() != null)
            sqlQuery += " and z.PLANOWANE_ZAKONCZENIE <= :PLANOWANE_ZAKONCZENIE_DO ";

        if (zc.getDataZakOd() != null)
            sqlQuery += " and z.PLANOWANE_ZAKONCZENIE >= :PLANOWANE_ZAKONCZENIE_OD ";

        return sqlQuery;
    }

    private Query<Object[]> setParameters(Query<Object[]> q, ZlecenieCriteria zc) {
        if (!zc.getDzialy().isEmpty())
            q.setParameterList("NR_DZIALU", zc.getDzialy().stream().map(Dzial::getId).collect(Collectors.toList()));

        if (!zc.getPojazdy().isEmpty())
            q.setParameterList("POJAZDY_NR_POJAZDU", zc.getPojazdy().stream().map(Pojazd::getNr_pojazdu).collect(Collectors.toList()));


        if (zc.getCenaDo() != null)
            q.setParameter("cenaDo", zc.getCenaDo());

        if (zc.getCenaOd() != null)
            q.setParameter("cenaOd", zc.getCenaOd());

        if (zc.getDataOdbDo() != null)
            q.setParameter("DATA_ODBIORU_DO", zc.getDataOdbDo());

        if (zc.getDataOdbOd() != null)
            q.setParameter("DATA_ODBIORU_OD", zc.getDataOdbOd());

        if (zc.getDataWplywuDo() != null)
            q.setParameter("DATA_WPLYWU_DO", zc.getDataWplywuDo());

        if (zc.getDataWplywuOd() != null)
            q.setParameter("DATA_WPLYWU_OD", zc.getDataWplywuOd());

        if (zc.getDataZakDo() != null)
            q.setParameter("PLANOWANE_ZAKONCZENIE_DO", zc.getDataZakDo());

        if (zc.getDataZakOd() != null)
            q.setParameter("PLANOWANE_ZAKONCZENIE_OD", zc.getDataZakOd());

        return q;
    }

    private Zlecenie castToZlecenie(Object[] z) {
        Zlecenie zlecenie = new Zlecenie();
        zlecenie.setId(((BigDecimal) z[0]).longValue());
        zlecenie.setDataWplywu((Date) z[1]);
        zlecenie.setPlanowaneZakonczenie((Date) z[2]);
        zlecenie.setDataOdbioru((Date) z[3]);
        zlecenie.setCena(((BigDecimal) z[4]).doubleValue());
        zlecenie.setIdPojazdu(((BigDecimal) z[5]).longValue());
        return zlecenie;
    }
}


