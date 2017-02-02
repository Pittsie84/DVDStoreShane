package dvdrental;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author gabor_000
 */
@Named(value = "actorController")
@SessionScoped
public class ActorController implements Serializable {

    // these fields map directly to components in the actor.xhtml
    String firstName;
    String lastName;
    String response;

    // this is our class that uses Hibernate to query the actor table
    ActorHelper helper;

    // this is our Actor POJO
    Actor actor;

    /**
     * Creates a new instance of ActorController
     */
    public ActorController() {
        helper = new ActorHelper();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResponse() {

        if (firstName != null && lastName != null) {

            // getting the current date in SQL format
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());

            // initializing an actor
            actor = new Actor(firstName, lastName, timeStamp);

            // calling our helper method that inserts a row into the
            // actor table
            if (helper.insertActor(actor) == 1) {
                // insert was successful
                firstName = null;
                lastName = null;
                response = "Actor Added.";
                return response;
            } else {
                // insert failed
                firstName = null;
                lastName = null;
                response = "Actor Not Added.";
                return response;
            }
        } else {
            // don't display message when first and last name are not input
            response = " ";
            return response;
        }
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
