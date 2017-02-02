
package dvdrental;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
/**
 *
 * @author gabor_000
 */
public class ActorHelper {
    
    Session session = null;
    
    public ActorHelper(){
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }  catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public int insertActor(Actor a){
        int result = 0;
        
        String sql = "insert into actor(first_name, last_name, last_update) "
                + "values (:fname, :lname, :update)";
        try{
            // starting a transaction if one isn't active
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            //creating an actual query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            // associating our Actor POJO and table with the query
            q.addEntity(Actor.class);
            
            // binding values to the placeholders in the query
            q.setParameter("fname", a.getFirstName());
            q.setParameter("lname", a.getLastName());
            q.setParameter("update", a.getLastUpdate());
            
            // executing the query
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
}
