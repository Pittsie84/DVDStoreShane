package dvdrental;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author gabor_000
 */
public class FilmActorHelper {

    Session session = null;

    public FilmActorHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getActors() {

        List<Actor> actorList = null;

        String sql = "select * from actor";

        try {

            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(Actor.class);

            actorList = (List<Actor>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return actorList;
    }

    public List getCategory() {

        List<Category> categoryList = null;

        String sql = "select * from category";

        try {

            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(Category.class);

            categoryList = (List<Category>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    public List getLanguage() {

        // setting local variable that will be used to return list
        // of lanuages
        List<Language> languageList = null;

        // creating query as a String
        String sql = "select * from language";

        try {
            // if current transaction isn't active, begin one
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);

            // associating the Language POJO and table with the query
            q.addEntity(Language.class);

            // executing the query
            languageList = (List<Language>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return languageList;
    }

    // this method inserts into film table
    private int insertFilm(String title, String description, int language,
            String rating, Timestamp timeStamp) {

        int result = 0;

        String sql = "insert into film "
                + "(title, description, language_id, rental_duration, rental_rate"
                + "replacement_cost, rating, last_update) "
                + "values (:title, :description, :languageId, :rentalDuration, "
                + ":rentalRate, :replacementCost, :rating, :update)";
        try {
            // if current transaction isn't active, begin one
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(Film.class);

            q.setParameter("title", title);
            q.setParameter("description", description);
            q.setParameter("languageId", language);
            q.setParameter("rentalDuration", 3);
            q.setParameter("rentalRate", 4.99);
            q.setParameter("replacementCost", 19.99);
            q.setParameter("rating", rating);
            q.setParameter("update", timeStamp);

            result = q.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private int getFilmId() {

        List<Film> filmList = null;

        String sql = "select * from film order by last_update decs limit 1";

        try {
            // if current transaction isn't active, begin one
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(Film.class);

            filmList = (List<Film>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filmList.get(0).getFilmId();
    }

    private int insertFilmActor(int actor, int film, Timestamp timeStamp) {

        int result = 0;

        String sql = "insert into film_actor values (:actorId, :filmId, :update)";

        try {
            // if current transaction isn't active, begin one
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(FilmActor.class);

            q.setParameter("actorId", actor);
            q.setParameter("description", film);
            q.setParameter("update", timeStamp);

            result = q.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    private int insertFilmCategory(int film, int category, Timestamp timeStamp){
    
            int result = 0;

        String sql = "insert into film_category values (:filmId, :categoryId, :update)";

        try {
            // if current transaction isn't active, begin one
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);

            q.addEntity(FilmCategory.class);

            q.setParameter("categoryId", category);
            q.setParameter("description", film);
            q.setParameter("update", timeStamp);

            result = q.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public int insert(String title, String description, int actor, int category,
            int language, String rating, Timestamp timeStamp){
        
        int result = 0;
        
        int filmResult = insertFilm(title, description, language, rating, timeStamp);
        int filmId = getFilmId();
        int actorResult = insertFilmActor(actor, filmId, timeStamp);
        int categoryResult = insertFilmCategory(filmId, category, timeStamp);
        
        if(filmResult == 1 && actorResult == 1 && categoryResult == 1){
            result = 1;
        }
        
        return result;
    }
}
