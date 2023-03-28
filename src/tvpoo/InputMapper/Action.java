package tvpoo.InputMapper;

import tvpoo.Movies.Movie;
import tvpoo.SortFilters.Filters;
import tvpoo.Users.Credentials;

public final class Action {
    /**
     * type - on page/ change page
     * page - page on which we want to do an action
     * movie - movie on which we want purchase on "see details" page
     * feature - name of the action we want to do on the page
     * credentials - information about a user who want to log in/register
     * startsWith - list all the movies that starts with a given String
     * filters - sorts the movies rate first and second after time that movie has
     * count - obtain the number of tokens that we want to buy
     */
    private int count;
    private int rate;
    private String type;
    private String page;
    private String movie;
    private String feature;
    private String startsWith;
    private String deletedMovie;
    private String subscribedGenre;
    private Filters filters;
    private Credentials credentials = new Credentials();
    private final Movie addedMovie = new Movie();
    /**
     * @return - obtain the type of the movie
     */
    public String getType() {
        return type;
    }
    /**
     * @param type - change the type of the movie
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     * @return - obtain the current page name
     */
    public String getPage() {
        return page;
    }
    /**
     * @param page - change the current page name
     */
    public void setPage(final String page) {
        this.page = page;
    }
    /**
     * @return - obtain the movie name
     */
    public String getMovie() {
        return movie;
    }
    /**
     * @param movie - change the movie name
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }
    /**
     * @return - it offers the action that you can DO on the page
     */
    public String getFeature() {
        return feature;
    }
    /**
     * @param feature - change the action that you can DO on the page
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }
    /**
     * @return - obtain the personal data of the User
     */
    public Credentials getCredentials() {
        return credentials;
    }
    /**
     * @param credentials - change the personal data of the User
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
    /**
     * @return - filter action which starts with a string given string
     */
    public String getStartsWith() {
        return startsWith;
    }
    /**
     * @return - obtain the filter that show some Movies based on some characteristics
     */
    public Filters getFilters() {
        return filters;
    }
    /**
     * @return - obtain the nr of tokens that we want to buy
     */
    public int getCount() {
        return count;
    }
    /**
     * @return - obtain the rating of the movie
     */
    public int getRate() {
        return rate;
    }
    /**
     * @return - obtain the new movie that was added in the database
     */
    public Movie getAddedMovie() {
        return addedMovie;
    }
    /**
     * @return - obtain the movie that will be deleted
     */
    public String getDeletedMovie() {
        return deletedMovie;
    }
    /**
     * @return - obtain the genre type of the subscribed user
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }
}
