package dvdrental;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author gabor_000
 */
@Named(value = "filmController")
@SessionScoped
public class FilmController implements Serializable {

    int startId;

    DataModel filmTitles;

    FilmHelper helper;

    private int recordCount;
    private int pageSize = 10;
    
    //Field used to get selected movie from index page
    private Film selected;
    
    // compeonents of the browse.xhtml
    String language;
    String actors;
    String category;

    /**
     * Creates a new instance of FilmController
     */
    public FilmController() {

        helper = new FilmHelper();
        startId = 0;
        recordCount = helper.getNumberFilms();
    }

    public DataModel getFilmTitles() {
        if (filmTitles == null) {
            filmTitles = new ListDataModel(helper.getFilmTitles(startId));
        }
        return filmTitles;
    }

    public void setFilmTitles(DataModel filmTitles) {
        this.filmTitles = filmTitles;
    }

    // this method sets filmTitles to null
    // if the field is null when index loads
    // get more films
    private void recreateModel() {
        filmTitles = null;
        recordCount = helper.getNumberFilms();
    }
    
    // this method gets caled when the next hyperlink gets called 
    // it increments the startId by the pageSize and forces
    // the page to get more films when it reloads
    public String next(){
        startId = startId + (pageSize + 1);
        recreateModel();
        return "index";
    }
    
    // this method gets caled when the previous hyperlink gets called 
    // it decrements the startId by the pageSize and forces
    // the page to get more films when it reloads
    public String previous(){
        startId = startId - (pageSize + 1);
        recreateModel();
        return "index";
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    // returns true when next values are possible
    public boolean isHasNextPage(){
        if (startId + pageSize < recordCount){
            return true;
        }
        return false;
    }
    
    // returns true when there are previous values
    public boolean isHasPreviousPage(){
        if (startId - pageSize > 0){
            return true;
        }
        return false;
    }

    public Film getSelected() {
        if (selected == null){
            selected = new Film();
        }
        return selected;
    }

    public void setSelected(Film selected) {
        this.selected = selected;
    }

    public String getLanguage() {
        int langId = selected.getLanguageByLanguageId().getLanguageId().intValue();
        language = helper.getLangByID(langId);
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getActors() {
        List actors = helper.getActorsByID(selected.getFilmId());
        StringBuilder cast = new StringBuilder();
        for(int i = 0; i < actors.size(); i++){
            Actor actor = (Actor) actors.get(i);
            cast.append(actor.getFirstName());
            cast.append(" ");
            cast.append(actor.getLastName());
            cast.append(" ");
        }
        return cast.toString();
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCategory() {
        Category category = helper.getCategoryByID(selected.getFilmId());
        return category.getName();
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String prepareView(){
        selected = (Film) getFilmTitles().getRowData();
        return "browse";
    }
}
