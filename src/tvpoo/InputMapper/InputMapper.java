package tvpoo.InputMapper;

import tvpoo.Movies.Movie;
import tvpoo.Users.User;
import java.util.ArrayList;

public final class InputMapper {
    /**
     * IN JSON FILE WE LL FIND ONLY 3 BIG ARRAY NODES:
     * first one for actions, then movies and last for users
     * in this precisely order will be found in each JSON basic_[nr].json
     */
    private ArrayList<Action> actionsMappedIN = new ArrayList<>();
    private ArrayList<Movie> moviesMappedIN = new ArrayList<>();
    private ArrayList<User> usersMappedIN = new ArrayList<>();
    /**
     * @return - obtain all the actions that we want to DO that are provided in database
     */
    public ArrayList<Action> getActions() {
        return actionsMappedIN;
    }
    /**
     * @param actions - change all the actions that we want to DO that are provided in database
     */
    public void setActions(final ArrayList<Action> actions) {
        this.actionsMappedIN = actions;
    }
    /**
     * @return - obtain the whole list of movies that are provided in database
     */
    public ArrayList<Movie> getMovies() {
        return moviesMappedIN;
    }
    /**
     * @param movies - change the whole list of movies that are provided in database
     */
    public void setMovies(final ArrayList<Movie> movies) {
        this.moviesMappedIN = movies;
    }
    /**
     * @return - obtain a list of Users that are provided in database
     */
    public ArrayList<User> getUsers() {
        return usersMappedIN;
    }
    /**
     * @param users - change a list of Users that are provided in database
     */
    public void setUsers(final ArrayList<User> users) {
        this.usersMappedIN = users;
    }
}
