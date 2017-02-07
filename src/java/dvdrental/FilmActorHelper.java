
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
    
    public FilmActorHelper(){
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }  catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public List getActors(){
        
        List<Actor> actorList = null;
        
        String sql = "select * from actor";
        
        try{
            
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Actor.class);
            
            actorList = (List<Actor>) q.list();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return actorList;
    }
    
    public List getCategory(){
        
        List<Category> categoryList = null;
        
        String sql = "select * from category";
        
        try{
            
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Category.class);
            
            categoryList = (List<Category>) q.list();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return categoryList;
    }
    
    public List getLanguage(){
        
        // setting local variable that will be used to return list
        // of lanuages
        List<Language> languageList = null;
        
        // creating query as a String
        String sql = "select * from language";
        
        try{
            // if current transaction isn't active, begin one
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            // associating the Language POJO and table with the query
            q.addEntity(Language.class);
            
            // executing the query
            languageList = (List<Language>) q.list();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return languageList;
    }
}

