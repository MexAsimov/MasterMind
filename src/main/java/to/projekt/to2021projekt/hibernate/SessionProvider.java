package to.projekt.to2021projekt.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import to.projekt.to2021projekt.models.Difficulty;
import to.projekt.to2021projekt.models.Game;
import to.projekt.to2021projekt.models.User;

public class SessionProvider {
    private static SessionFactory sessionFactory = null;
    private static User loggedUser = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory =
                    configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        SessionProvider.loggedUser = loggedUser;
    }

    public static Session getSession(){
        return getSessionFactory().openSession();
    }

    public static void saveGame(Game game){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(game);
        tx.commit();
        session.close();

    }
    public static Game getBestScore(){
        String hql = "FROM Game g ORDER BY g.result DESC";
        Query query = SessionProvider.getSession().createQuery(hql);
        if(!query.list().isEmpty())
            return (Game)query.list().get(0);
        else
            return null;
    }
    public static Difficulty findDifficulty(Difficulty difficulty){
        String hql = "FROM Difficulty d where d.numOfColors = :colors and d.numOfRounds = :rounds " +
                "and d.colorRepetition = :colorRep";
        Session session = SessionProvider.getSession();
        Query query = session.createQuery(hql).
                setParameter("colors", difficulty.getNumOfColors()).
                setParameter("rounds", difficulty.getNumOfRounds()).
                setParameter("colorRep", difficulty.getColorRepetition());
        if(query.list().isEmpty()){
            session.close();
            return difficulty;
        }
        Difficulty result = (Difficulty) query.list().get(0);
        session.close();

        return result;
    }
}
